package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.cart.add.CartAddItemOperation;
import com.example.tinqin.bff.api.operation.cart.add.CartAddItemRequest;
import com.example.tinqin.bff.api.operation.cart.add.CartAddItemResponse;
import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemOperation;
import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemRequest;
import com.example.tinqin.bff.api.operation.cart.remove.CartRemoveItemResponse;
import com.example.tinqin.bff.api.operation.cart.view.CartViewOperation;
import com.example.tinqin.bff.api.operation.cart.view.CartViewRequest;
import com.example.tinqin.bff.api.operation.cart.view.CartViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartAddItemOperation cartAddItemOperation;
    private final CartRemoveItemOperation cartRemoveItemOperation;
    private final CartViewOperation cartViewOperation;

    @Autowired
    public CartController(CartAddItemOperation cartAddItemOperation, CartRemoveItemOperation cartRemoveItemOperation, CartViewOperation cartViewOperation) {
        this.cartAddItemOperation = cartAddItemOperation;
        this.cartRemoveItemOperation = cartRemoveItemOperation;
        this.cartViewOperation = cartViewOperation;
    }

    @PostMapping("/add")
    public ResponseEntity<CartAddItemResponse> addToCart
            (Principal principal,
             @RequestParam(name = "itemId") String itemId,
             @RequestParam(name = "quantity") String quantity){
        CartAddItemRequest cartAddItemRequest=CartAddItemRequest
                .builder()
                .itemId(UUID.fromString(itemId))
                .username(principal.getName())
                .quantity(Integer.valueOf(quantity))
                .build();
        return ResponseEntity.ok(cartAddItemOperation.process(cartAddItemRequest));

    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<CartRemoveItemResponse> removeFromCart
            (Principal principal,
             @RequestParam(name = "itemId") String itemId) {
        CartRemoveItemRequest cartRemoveItemRequest = CartRemoveItemRequest
                .builder()
                .username(principal.getName())
                .itemId(UUID.fromString(itemId))
                .build();
        return ResponseEntity.ok(cartRemoveItemOperation.process(cartRemoveItemRequest));
    }

    @PostMapping
    public ResponseEntity<CartViewResponse> viewCart
            (Principal principal){
        CartViewRequest cartViewRequest = CartViewRequest
                .builder()
                .username(principal.getName())
                .build();
        return ResponseEntity.ok(cartViewOperation.process(cartViewRequest));
    }
}
