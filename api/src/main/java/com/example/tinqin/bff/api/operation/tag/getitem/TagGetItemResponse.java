package com.example.tinqin.bff.api.operation.tag.getitem;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagGetItemResponse implements OperationResponse {
    private List<ItemResponse> tagItemSet;
    Integer page;
    Integer size;
    Long totalItems;
}
