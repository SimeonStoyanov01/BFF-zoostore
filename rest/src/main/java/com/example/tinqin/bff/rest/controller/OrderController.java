package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.order.OrderOperation;
import com.example.tinqin.bff.api.operation.order.OrderRequest;
import com.example.tinqin.bff.api.operation.order.OrderResponse;
import com.example.tinqin.bff.persistence.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class OrderController {
    private final OrderOperation orderOperation;
    @Autowired
    public OrderController(OrderOperation orderOperation) {
        this.orderOperation = orderOperation;
    }
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> makeOrder
            (Principal principal,
             Boolean bool,
             PaymentType paymentType){
        OrderRequest orderRequest = OrderRequest
                .builder()
                .username(principal.getName())
                .payment(bool)
                .paymentType(paymentType)
                .build();

        return ResponseEntity.ok(orderOperation.process(orderRequest));


    }

}
