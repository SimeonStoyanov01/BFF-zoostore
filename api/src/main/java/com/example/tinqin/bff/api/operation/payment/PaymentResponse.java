package com.example.tinqin.bff.api.operation.payment;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse implements OperationResponse {
    private Boolean payment;
}
