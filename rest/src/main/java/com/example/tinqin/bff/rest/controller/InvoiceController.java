package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.invoice.InvoiceConvertOperation;
import com.example.tinqin.bff.api.operation.invoice.InvoiceOperationRequest;
import com.example.tinqin.bff.api.operation.invoice.InvoiceOperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceConvertOperation invoiceConvertOperation;

    @GetMapping("/invoice")
    public ResponseEntity<byte[]> generateInvoice(String orderId, Principal principal) {
        InvoiceOperationRequest operationRequest =
                InvoiceOperationRequest
                        .builder()
                        .orderId(orderId)
                        .username(principal.getName())
                        .build();
        InvoiceOperationResponse invoiceResponse = invoiceConvertOperation.process(operationRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        return ResponseEntity.ok().headers(headers).body(invoiceResponse.getPdfFile());
    }

}
