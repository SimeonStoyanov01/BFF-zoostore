package com.example.tinqin.bff.api.operation.cart.remove;

import com.example.tinqin.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRemoveItemRequest implements OperationRequest {
    @NotBlank(message = "userId field is empty")
    private String username;
    @NotBlank(message = "itemId field is empty")
    private UUID itemId;

}
