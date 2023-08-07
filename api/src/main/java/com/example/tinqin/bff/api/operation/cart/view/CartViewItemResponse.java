package com.example.tinqin.bff.api.operation.cart.view;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartViewItemResponse {
    private UUID itemId;
    private String itemTitle;
    private String itemVendor;
    private Integer quantity;
    private BigDecimal totalPrice;
}
