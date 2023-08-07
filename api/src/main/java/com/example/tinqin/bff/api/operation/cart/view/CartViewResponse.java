package com.example.tinqin.bff.api.operation.cart.view;

import com.example.tinqin.bff.api.base.OperationResponse;
import com.example.tinqin.bff.api.operation.cart.add.CartAddItemResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartViewResponse implements OperationResponse {
    private String username;
    private Set<CartViewItemResponse> cartViewItemResponseSet;
    private BigDecimal bill;
}
