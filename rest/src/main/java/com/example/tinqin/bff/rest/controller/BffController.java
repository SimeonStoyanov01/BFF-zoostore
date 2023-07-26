package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdOperation;
import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdRequest;
import com.example.tinqin.bff.api.operation.item.get.BffItemGetByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bffitem")
public class BffController {

    private final BffItemGetByIdOperation getByIdOperation;

    public BffController(BffItemGetByIdOperation getByIdOperation) {
        this.getByIdOperation = getByIdOperation;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BffItemGetByIdResponse> getItem(BffItemGetByIdRequest bffItemGetByIdRequest){
        return ResponseEntity.ok(getByIdOperation.process(bffItemGetByIdRequest));
    }
}
