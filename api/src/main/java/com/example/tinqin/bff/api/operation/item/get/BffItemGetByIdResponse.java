package com.example.tinqin.bff.api.operation.item.get;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BffItemGetByIdResponse implements OperationResponse {
    private UUID id;
    private String title;
    private String vendor;
    private BigDecimal price;
    private Integer quantity;
}
