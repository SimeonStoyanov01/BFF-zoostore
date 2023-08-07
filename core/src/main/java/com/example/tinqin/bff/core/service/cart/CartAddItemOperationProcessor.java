package com.example.tinqin.bff.core.service.cart;

import com.example.tinqin.bff.api.operation.cart.add.CartAddItemOperation;
import com.example.tinqin.bff.api.operation.cart.add.CartAddItemRequest;
import com.example.tinqin.bff.api.operation.cart.add.CartAddItemResponse;
import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdOperation;
import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdRequest;
import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdResponse;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.CartItemEntity;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.CartItemRepository;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CartAddItemOperationProcessor implements CartAddItemOperation {
    private final BffItemGetByIdOperation bffGetByIdOperation;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    @Autowired
    public CartAddItemOperationProcessor(BffItemGetByIdOperation bffGetByIdOperation, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.bffGetByIdOperation = bffGetByIdOperation;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartAddItemResponse process(CartAddItemRequest operationRequest) {

        BffItemGetByIdResponse refItem =
                bffGetByIdOperation
                        .process
                (BffItemGetByIdRequest
                        .builder()
                        .id(operationRequest
                                .getItemId()
                                .toString())
                        .build());

        UserEntity user =
                userRepository
                .findUserEntityByEmail(operationRequest
                        .getUsername())
                .orElseThrow(NoUserExistsException::new);

        CartItemEntity cartItem =
                cartItemRepository
                .findCartItemEntityByItemIdAndUserId
                        (operationRequest.getItemId(),
                        user.getId())
                .orElse(
                        CartItemEntity.builder()
                                .itemId(refItem.getId())
                                .userId(user.getId())
                                .quantity(0)
                                .build()
                );


        if ((operationRequest.getQuantity() + cartItem.getQuantity()) < refItem.getQuantity()) {

            cartItem.setQuantity(operationRequest.getQuantity() + cartItem.getQuantity());

        }


        BigDecimal calcPrice = new BigDecimal(cartItem.getQuantity());

        calcPrice = calcPrice.multiply(refItem.getPrice());

        cartItem.setPrice(calcPrice);

        cartItem.setUserId(user.getId());

        cartItemRepository.save(cartItem);

        return CartAddItemResponse
                .builder()
                .cartItemId(cartItem.getCartItemId())
                .username(user.getEmail())
                .itemVendor(refItem.getVendor())
                .itemTitle(refItem.getTitle())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getPrice())
                .build();
    }
}
