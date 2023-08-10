package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.payment.PaymentOperation;
import com.example.tinqin.bff.api.operation.payment.PaymentRequest;
import com.example.tinqin.bff.api.operation.payment.PaymentResponse;
import com.example.tinqin.bff.persistence.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
//TODO payment service is to be implemented. For now it is always successful

@RestController
public class PaymentController {
    private final PaymentOperation paymentOperation;
    @Autowired
    public PaymentController(PaymentOperation paymentOperation) {
        this.paymentOperation = paymentOperation;
    }
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment
            (Principal principal,
             PaymentType paymentType){
        PaymentRequest paymentRequest=PaymentRequest
                .builder()
                .username(principal.getName())
                .paymentType(PaymentType.CASH)
                .build();
        return ResponseEntity.ok(paymentOperation.process(paymentRequest));
    }
}
