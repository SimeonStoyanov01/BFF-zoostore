package com.example.tinqin.bff.api.operation.invoice;

import com.example.tinqin.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceOperationRequest implements OperationRequest {
    @NotBlank(message = "orderId field is empty")
    private String orderId;

    private String username;
}
