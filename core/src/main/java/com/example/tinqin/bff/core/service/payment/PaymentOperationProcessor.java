package com.example.tinqin.bff.core.service.payment;

import com.example.tinqin.bff.api.operation.payment.PaymentOperation;
import com.example.tinqin.bff.api.operation.payment.PaymentRequest;
import com.example.tinqin.bff.api.operation.payment.PaymentResponse;
import org.springframework.stereotype.Service;

//TODO payment service is to be implemented. For now it is always successful
@Service
public class PaymentOperationProcessor implements PaymentOperation {

    @Override
    public PaymentResponse process(PaymentRequest operationRequest) {
        return PaymentResponse
                .builder()
                .payment(true)
                .build();
    }
}
