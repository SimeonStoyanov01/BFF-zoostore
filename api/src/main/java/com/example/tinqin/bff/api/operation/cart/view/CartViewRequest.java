package com.example.tinqin.bff.api.operation.cart.view;

import com.example.tinqin.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartViewRequest implements OperationRequest {
    @NotBlank(message = "userId field is empty")
    private String username;


}
