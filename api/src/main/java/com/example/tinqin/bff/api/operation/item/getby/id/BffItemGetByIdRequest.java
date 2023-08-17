package com.example.tinqin.bff.api.operation.item.getby.id;


import com.example.tinqin.bff.api.base.OperationRequest;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffItemGetByIdRequest implements OperationRequest {
    private String id;
}
