package com.example.tinqin.bff.api.operation.invoice;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceOperationResponse implements OperationResponse {
    private byte[] pdfFile;
}
