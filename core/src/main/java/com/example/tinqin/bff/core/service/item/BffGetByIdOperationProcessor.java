package com.example.tinqin.bff.core.service.item;

import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdOperation;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdRequest;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdResponse;
import com.example.tinqin.bff.restexport.ZooStoreStorageRestClient;
import com.example.tinqin.zoostore.API.operation.item.getby.id.ItemGetByIdResponse;
import com.example.tinqin.zoostore.restexport.ZooStoreRestClient;
import com.example.tinqin.zoostorestorage.API.operation.item.getbyid.StorageItemGetByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BffGetByIdOperationProcessor implements BffItemGetByIdOperation {
    private final ZooStoreRestClient zooStoreRestClient;
    private final ZooStoreStorageRestClient zooStoreStorageRestClient;

    @Autowired
    public BffGetByIdOperationProcessor(ZooStoreRestClient zooStoreRestClient,
                                        ZooStoreStorageRestClient zooStoreStorageRestClient) {
        this.zooStoreRestClient = zooStoreRestClient;
        this.zooStoreStorageRestClient = zooStoreStorageRestClient;

    }

    @Override
    public BffItemGetByIdResponse process(BffItemGetByIdRequest operationRequest) {

        try {
            zooStoreRestClient.itemGetById(operationRequest.getId());
        } catch (Exception e) {
            throw new RuntimeException("ZooStoreClientFactory: item does not exist");
        }
        try {
            zooStoreStorageRestClient.getStorageItemById(operationRequest.getId());
        } catch (Exception e) {
            throw new RuntimeException("StorageClientFactory: item does not exist");
        }
        ItemGetByIdResponse item = zooStoreRestClient.itemGetById(operationRequest.getId());
        StorageItemGetByIdResponse storage = zooStoreStorageRestClient.getStorageItemById(operationRequest.getId());


        return BffItemGetByIdResponse
                .builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(storage.getPrice())
                .quantity(storage.getQuantity())
                .vendor(item.getVendor())
                .build();
    }
}
