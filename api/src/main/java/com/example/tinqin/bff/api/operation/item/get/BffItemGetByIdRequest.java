package com.example.tinqin.bff.api.operation.item.get;


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
