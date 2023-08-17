package com.example.tinqin.bff.core.service.cart;

import com.example.tinqin.bff.api.operation.cart.view.CartViewItemResponse;
import com.example.tinqin.bff.api.operation.cart.view.CartViewOperation;
import com.example.tinqin.bff.api.operation.cart.view.CartViewRequest;
import com.example.tinqin.bff.api.operation.cart.view.CartViewResponse;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdOperation;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdRequest;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdResponse;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.CartItemEntity;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.CartItemRepository;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartViewOperationProcessor implements CartViewOperation {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final BffItemGetByIdOperation bffGetByIdOperation;

    @Autowired
    public CartViewOperationProcessor(UserRepository userRepository, CartItemRepository cartItemRepository, BffItemGetByIdOperation bffGetByIdOperation) {
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.bffGetByIdOperation = bffGetByIdOperation;
    }

    @Override
    public CartViewResponse process(CartViewRequest operationRequest) {

        UserEntity user =
                userRepository
                        .findUserEntityByEmail(operationRequest
                                .getUsername())
                        .orElseThrow(NoUserExistsException::new);

        Set<CartItemEntity> cartItem =
                cartItemRepository.findAllByUserId(user.getId());

        Set<CartViewItemResponse> cartViewItemResponseSet =
                cartItem
                .stream()
                .map(item -> {
                    BffItemGetByIdResponse refItem =
                            bffGetByIdOperation
                                    .process(BffItemGetByIdRequest
                                    .builder()
                                    .id(item.getItemId().toString())
                                    .build());

                    return CartViewItemResponse
                            .builder()
                            .itemId(item.getItemId())
                            .itemTitle(refItem.getTitle())
                            .itemVendor(refItem.getVendor())
                            .quantity(item.getQuantity())
                            .totalPrice(item.getPrice())
                            .build();
                })
                .collect(Collectors.toSet());

        BigDecimal bill = cartItem
                .stream()
                .map(CartItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return CartViewResponse
                .builder()
                .username(user.getUsername())
                .cartViewItemResponseSet(cartViewItemResponseSet)
                .bill(bill)
                .build();
    }
}
