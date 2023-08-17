package com.example.tinqin.bff.api.operation.item.getby.title;

import com.example.tinqin.bff.api.base.OperationResponse;
import com.example.tinqin.bff.api.operation.tag.getitem.ItemResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemGetByTitleResponse implements OperationResponse {
    private List<ItemResponse> itemResponse = new ArrayList<>();
    Integer page;
    Integer size;
    Long totalItems;

}
