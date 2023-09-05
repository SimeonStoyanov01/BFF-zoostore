package com.example.tinqin.bff.core.service.invoice;

import com.example.tinqin.bff.api.operation.invoice.*;
import com.example.tinqin.bff.core.exceptions.NoSuchOrderExistsException;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.OrderEntity;
import com.example.tinqin.bff.persistence.entity.ProductStatus;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.OrderRepository;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import com.example.tinqin.zoostore.API.operation.item.getby.id.ItemGetByIdResponse;
import com.example.tinqin.zoostore.restexport.ZooStoreRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceConverter {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ZooStoreRestClient zooStoreRestClient;

    @Autowired
    public InvoiceConverter(OrderRepository orderRepository, UserRepository userRepository, ZooStoreRestClient zooStoreRestClient) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.zooStoreRestClient = zooStoreRestClient;
    }

    public InvoiceTemplateDataInput convert(InvoiceOperationRequest operationRequest) {

        OrderEntity invoiceData = this.orderRepository
                .findOrderEntityByOrderId(UUID.fromString(operationRequest.getOrderId()))
                .orElseThrow(NoSuchOrderExistsException::new);

        UserEntity invoiceUser = this.userRepository
                .findUserEntityByEmail(operationRequest.getUsername())
                .orElseThrow(NoUserExistsException::new);

        Set<InvoiceItemResponse> invoiceItems =
                invoiceData
                        .getCartItemEntities()
                        .stream()
                        .filter(cartItemEntity ->
                                cartItemEntity.getProductStatus() == ProductStatus.BOUGHT)
                        .map(cartItemEntity -> {
                            ItemGetByIdResponse itemGetByIdResponse = zooStoreRestClient.itemGetById(String.valueOf(cartItemEntity.getItemId()));
                            BigDecimal totalPrice = cartItemEntity.getPrice().multiply(BigDecimal.valueOf(cartItemEntity.getQuantity()));

                            return InvoiceItemResponse.builder()
                                    .itemId(String.valueOf(cartItemEntity.getItemId()))
                                    .itemTitle(itemGetByIdResponse.getTitle())
                                    .quantity(cartItemEntity.getQuantity())
                                    .price(cartItemEntity.getPrice())
                                    .totalPrice(totalPrice)
                                    .build();
                        })
                        .collect(Collectors.toSet());

        BigDecimal totalBill = invoiceItems.stream()
                .map(invoiceItem -> new BigDecimal(String.valueOf(invoiceItem.getTotalPrice())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return InvoiceTemplateDataInput.builder()
                .userId(String.valueOf(invoiceUser.getId()))
                .firstName(invoiceUser.getFirstName())
                .lastName(invoiceUser.getLastName())
                .invoiceItems(invoiceItems)
                .bill(totalBill)
                .timeOfSale(Timestamp.from(Instant.now()))
                .build();
    }
}
