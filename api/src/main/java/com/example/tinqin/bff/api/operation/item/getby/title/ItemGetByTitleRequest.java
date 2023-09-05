package com.example.tinqin.bff.api.operation.item.getby.title;


import com.example.tinqin.bff.api.base.OperationRequest;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemGetByTitleRequest implements OperationRequest {
    private String title;
    private Integer page;
    private Integer size;
}
