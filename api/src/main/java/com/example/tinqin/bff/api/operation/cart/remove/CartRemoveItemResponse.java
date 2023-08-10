package com.example.tinqin.bff.api.operation.cart.remove;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRemoveItemResponse implements OperationResponse {

    private String message;
}
