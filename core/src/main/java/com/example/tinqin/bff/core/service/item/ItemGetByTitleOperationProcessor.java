package com.example.tinqin.bff.core.service.item;

import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleOperation;
import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleRequest;
import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleResponse;
import com.example.tinqin.bff.api.operation.tag.getitem.ItemResponse;
import com.example.tinqin.bff.core.exceptions.NoSuchItemExistsException;
import com.example.tinqin.bff.restexport.ZooStoreStorageRestClient;
import com.example.tinqin.zoostore.API.operation.item.getby.title.GetItemsResponse;
import com.example.tinqin.zoostore.restexport.ZooStoreRestClient;
import com.example.tinqin.zoostorestorage.API.operation.item.getbyid.StorageItemGetByIdResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class ItemGetByTitleOperationProcessor implements ItemGetByTitleOperation {
    private final ZooStoreStorageRestClient zooStoreStorageRestClient;
    private final ZooStoreRestClient zooStoreRestClient;

    @Autowired
    public ItemGetByTitleOperationProcessor(ZooStoreStorageRestClient zooStoreStorageRestClient, ZooStoreRestClient zooStoreRestClient) {
        this.zooStoreStorageRestClient = zooStoreStorageRestClient;
        this.zooStoreRestClient = zooStoreRestClient;
    }

    @Override
    public ItemGetByTitleResponse process(ItemGetByTitleRequest operationRequest) {

com.example.tinqin.zoostore.API.operation.item.getby.title.ItemGetByTitleResponse itemsFromZooStore=zooStoreRestClient.getItemsByTitle
                (operationRequest.getTitle(),
                        operationRequest.getPage(),
                        operationRequest.getSize());

        List<String> uuidList= itemsFromZooStore
                .getAllItems()
                .stream()
                .map(GetItemsResponse::getId)
                .toList();

        List<GetItemsResponse> storeItemList=itemsFromZooStore.getAllItems();

                            List<StorageItemGetByIdResponse> storageItemList;
                            try{
                                storageItemList = uuidList
                                        .parallelStream()
                                        .map(itemId -> {
                                            StorageItemGetByIdResponse response = zooStoreStorageRestClient.getStorageItemById(itemId);
                                            if (!Objects.equals(response.getId(), "00000000-0000-0000-0000-000000000000")) {
                                                return StorageItemGetByIdResponse.builder()
                                                        .id(response.getId())
                                                        .price(response.getPrice())
                                                        .quantity(response.getQuantity())
                                                        .build();
                                            }
                                            return null; // Return null for invalid responses
                        })
                        .filter(Objects::nonNull) // Filter out null responses
                        .toList();}
        catch(FeignException ex){
            throw new NoSuchItemExistsException();
        }
        ItemGetByTitleResponse itemGetByTitleResponse = ItemGetByTitleResponse
                .builder()
                .totalItems(itemsFromZooStore.getTotalItems())
                .page(itemsFromZooStore.getPage())
                .size(itemsFromZooStore.getSize())
                .build();

        storeItemList.forEach(i -> {
            ItemResponse itemResponse = ItemResponse
                    .builder()
                    .id(UUID.fromString(i.getId()))
                    .vendor(i.getVendorName())
                    .title(i.getTitle())
                    .multimediaUrl(i.getMultimediaUrl())
                    .tagTitle(i.getTagName())
                    .build();

            storageItemList
                    .stream()
                    .filter(s -> s.getId().equals(i.getId()))
                    .forEach(s -> {
                        itemResponse.setQuantity(s.getQuantity());
                        itemResponse.setPrice(s.getPrice());
                    });
            itemGetByTitleResponse.getItemResponse().add(itemResponse);

        });




        return itemGetByTitleResponse;
    }
}
