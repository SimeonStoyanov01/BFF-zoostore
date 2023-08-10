package com.example.tinqin.bff.core.service.cart;

import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemOperation;
import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemRequest;
import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemResponse;
import com.example.tinqin.bff.core.exceptions.NoSuchItemExistsException;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.CartItemEntity;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.CartItemRepository;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartRemoveItemOperationProcessor implements CartRemoveItemOperation {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    @Autowired
    public CartRemoveItemOperationProcessor(UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartRemoveItemResponse process(CartRemoveItemRequest operationRequest) {

        UserEntity user =
                userRepository
                        .findUserEntityByEmail(operationRequest
                                .getUsername())
                        .orElseThrow(NoUserExistsException::new);
        CartItemEntity cartItem =cartItemRepository.findCartItemEntityByItemIdAndUserId(operationRequest.getItemId(), user.getId()).orElseThrow(NoSuchItemExistsException::new);

        cartItemRepository.delete(cartItem);

        return CartRemoveItemResponse
                .builder()
                .message(String.format("Item %s was deleted successfully",operationRequest.getItemId()))
                .build();
    }
}
