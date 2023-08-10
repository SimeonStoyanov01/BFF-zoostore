package com.example.tinqin.bff.api.operation.payment;


import com.example.tinqin.bff.api.base.OperationRequest;
import com.example.tinqin.bff.persistence.entity.PaymentType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest implements OperationRequest {
    @NotBlank(message = "userId field is empty")
    private String username;
    @NotBlank(message = "payment type not specified")
    private PaymentType paymentType;

}
