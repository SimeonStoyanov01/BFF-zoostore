package com.example.tinqin.bff.core.service.order;

import com.example.tinqin.bff.api.operation.cart.view.CartViewItemResponse;
import com.example.tinqin.bff.api.operation.cart.view.CartViewOperation;
import com.example.tinqin.bff.api.operation.cart.view.CartViewRequest;
import com.example.tinqin.bff.api.operation.cart.view.CartViewResponse;
import com.example.tinqin.bff.api.operation.order.OrderOperation;
import com.example.tinqin.bff.api.operation.order.OrderRequest;
import com.example.tinqin.bff.api.operation.order.OrderResponse;
import com.example.tinqin.bff.api.operation.payment.PaymentOperation;
import com.example.tinqin.bff.api.operation.payment.PaymentRequest;
import com.example.tinqin.bff.core.exceptions.EmptyCartException;
import com.example.tinqin.bff.core.exceptions.NoSuchItemExistsException;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.CartItemEntity;
import com.example.tinqin.bff.persistence.entity.OrderEntity;
import com.example.tinqin.bff.persistence.entity.PaymentType;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.CartItemRepository;
import com.example.tinqin.bff.persistence.repository.OrderRepository;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import com.example.tinqin.bff.restexport.ZooStoreStorageRestClient;
import com.example.tinqin.zoostorestorage.API.operation.item.export.StorageItemExportRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderOperationProcessor implements OrderOperation {
    private final ZooStoreStorageRestClient storageRestClient;
    private final CartViewOperation cartViewOperation;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentOperation paymentOperation;

    public OrderOperationProcessor(ZooStoreStorageRestClient storageRestClient, CartViewOperation cartViewOperation, UserRepository userRepository, OrderRepository orderRepository, CartItemRepository cartItemRepository, PaymentOperation paymentOperation) {
        this.storageRestClient = storageRestClient;
        this.cartViewOperation = cartViewOperation;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.paymentOperation = paymentOperation;
    }

    @Override
    public OrderResponse process(OrderRequest operationRequest) {

        operationRequest.setPayment(true);

        OrderEntity order = new OrderEntity();

        //Set<CartViewItemResponse> boughtItems = new HashSet<>();;

        CartViewResponse cartView = cartViewOperation
                .process(CartViewRequest
                        .builder()
                        .username(operationRequest.getUsername())
                        .build());

        if(cartView.getCartViewItemResponseSet().isEmpty())
            throw new EmptyCartException();

        UserEntity user = userRepository
                .findUserEntityByEmail(operationRequest
                        .getUsername())
                .orElseThrow(NoUserExistsException::new);


        for (CartViewItemResponse cartItem : cartView.getCartViewItemResponseSet()) {
            CartItemEntity cartItemEntity = cartItemRepository
                    .findCartItemEntityByItemIdAndUserId
                            (cartItem.getItemId(),
                                    user.getId())
                    .orElseThrow(NoSuchItemExistsException::new);

//            order.getCartItemEntities().add(CartItemEntity
//                    .builder()
//                    .itemId(cartItem.getItemId())
//                    .quantity(cartItem.getQuantity())
//                    .userId(user.getId())
//                    .price(cartItem.getTotalPrice())
//                    .cartItemId(cartItemEntity.getCartItemId())
//                    .build());

            StorageItemExportRequest storageItemExportRequest = StorageItemExportRequest
                    .builder()
                    .itemId(cartItem.getItemId())
                    .quantity(cartItem.getQuantity())
                    .build();

            storageRestClient.exportItem(storageItemExportRequest);
            cartItemRepository.delete(cartItemEntity);
        }

        order.setBill(cartView.getBill());
        order.setUserId(user.getId());
        order.setPaymentType(operationRequest.getPaymentType());

        orderRepository.save(order);

        return OrderResponse
                .builder()
                .message(String.format("OrderID: %s \n " +
                                "Total: %s \n " +
                                "Payment type: %s \n " +
                                "User: %s \n\n " +
                                "%s \n",
                        order.getOrderId(),
                        order.getBill(),
                        order.getPaymentType(),
                        operationRequest.getUsername(),
                        order.getTimeOfSale()))
                .cartViewItemResponseSet(cartView.getCartViewItemResponseSet())
                .build();
    }
}
