package com.example.tinqin.bff.api.operation.tag.getitem;

import com.example.tinqin.bff.api.base.OperationRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagGetItemRequest implements OperationRequest {
    String title;
    Integer page;
    Integer size;
}
