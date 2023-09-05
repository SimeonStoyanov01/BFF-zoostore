package com.example.tinqin.bff.api.operation.invoice;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTemplateDataInput {
    private String userId;
    private String firstName;
    private String lastName;
    private Set<InvoiceItemResponse> invoiceItems;
    private BigDecimal bill;
    private Timestamp timeOfSale;

}
