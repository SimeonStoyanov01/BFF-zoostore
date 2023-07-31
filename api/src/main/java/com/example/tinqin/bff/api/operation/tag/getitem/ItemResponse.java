package com.example.tinqin.bff.api.operation.tag.getitem;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private UUID id;
    private String title;
    private String vendor;
    private Set<String> tagTitle;
    private Set<String> multimediaUrl;
    private BigDecimal price;
    private Integer quantity;
}
