package com.example.tinqin.bff.api.base;

public interface OperationProcessor<R extends OperationResponse,T extends OperationRequest>{
    R process(T operationRequest);
}
