package com.example.tinqin.bff.api.operation.invoice;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemResponse {
    private String itemId;
    private String itemTitle;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
