package com.example.tinqin.bff.api.operation.order;

import com.example.tinqin.bff.api.base.OperationResponse;
import com.example.tinqin.bff.api.operation.cart.view.CartViewItemResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse implements OperationResponse {
    private String message;
    private Set<CartViewItemResponse> cartViewItemResponseSet;

}
