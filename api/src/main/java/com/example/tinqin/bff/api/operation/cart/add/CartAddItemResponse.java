package com.example.tinqin.bff.api.operation.cart.add;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAddItemResponse implements OperationResponse {
    private UUID cartItemId;
    private String username;
    private String itemTitle;
    private String itemVendor;
    private Integer quantity;
    private BigDecimal totalPrice;
}
