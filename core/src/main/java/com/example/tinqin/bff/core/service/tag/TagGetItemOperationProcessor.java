package com.example.tinqin.bff.core.service.tag;

import com.example.tinqin.bff.api.operation.tag.getitem.ItemResponse;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemOperation;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemRequest;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemResponse;
import com.example.tinqin.bff.restexport.ZooStoreStorageRestClient;
import com.example.tinqin.zoostore.API.operation.tag.getitemsbytag.GetItemsResponse;
import com.example.tinqin.zoostore.API.operation.tag.getitemsbytag.TagGetItemsResponse;
import com.example.tinqin.zoostore.restexport.ZooStoreRestClient;
import com.example.tinqin.zoostorestorage.API.operation.item.getAll.GetCollectionOfStorageItemsByItemIdRequest;
import com.example.tinqin.zoostorestorage.API.operation.item.getAll.GetCollectionOfStorageItemsByItemIdResponse;
import com.example.tinqin.zoostorestorage.API.operation.item.getAll.StorageItemForCollectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagGetItemOperationProcessor implements TagGetItemOperation {
    private final ZooStoreRestClient zooStoreRestClient;
    private final ZooStoreStorageRestClient storageRestClient;

    @Autowired
    public TagGetItemOperationProcessor(ZooStoreRestClient zooStoreRestClient, ZooStoreStorageRestClient zooStoreStorageRestClient) {
        this.zooStoreRestClient = zooStoreRestClient;
        this.storageRestClient = zooStoreStorageRestClient;
    }

    @Override
    public TagGetItemResponse process(TagGetItemRequest operationRequest) {
        try {
            zooStoreRestClient.tagGetItem(operationRequest.getTitle(), operationRequest.getPage(), operationRequest.getSize());
        } catch (Exception e) {
            throw new RuntimeException("ZooStoreClientFactory: tag does not exist");
        }
        TagGetItemsResponse itemListFromZooStore = zooStoreRestClient.tagGetItem(operationRequest.getTitle(), operationRequest.getPage(), operationRequest.getSize());
        List<String> storageItemList = new ArrayList<>();

        for (GetItemsResponse item : itemListFromZooStore.getAllItems()) {
            storageItemList.add(item.getId().toString());
        }

        GetCollectionOfStorageItemsByItemIdRequest storageItemRequest = GetCollectionOfStorageItemsByItemIdRequest
                .builder()
                .itemId(storageItemList)
                .build();

        GetCollectionOfStorageItemsByItemIdResponse itemsFromStorage =
                storageRestClient
                        .getItemCollection(storageItemRequest);

        List<ItemResponse> collectionOfItems = new ArrayList<>();

        for (GetItemsResponse item : itemListFromZooStore.getAllItems()) {

            for (StorageItemForCollectionResponse st : itemsFromStorage.getStorageCollection()) {

                if (item.getId()
                        .equals(st.getId())) {

                    ItemResponse itemResponse = ItemResponse

                            .builder()
                            .id(UUID
                                    .fromString(item
                                            .getId()))
                            .title(item
                                    .getTitle())
                            .vendor(item
                                    .getVendorName())
                            .price(st.
                                    getPrice())
                            .multimediaUrl(item
                                    .getMultimediaUrl())
                            .tagTitle(item
                                    .getTagName())
                            .quantity(st
                                    .getQuantity())
                            .build();
                    collectionOfItems.add(itemResponse);
                }
            }

        }
        return TagGetItemResponse
                .builder()
                .tagItemSet(collectionOfItems)
                .page(itemListFromZooStore.getPage())
                .size(itemListFromZooStore.getSize())
                .totalItems(itemListFromZooStore.getTotalItems())
                .build();
    }
}
