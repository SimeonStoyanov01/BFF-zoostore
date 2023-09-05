package com.example.tinqin.bff.core.service.invoice;

import com.example.tinqin.bff.api.operation.invoice.InvoiceConvertOperation;
import com.example.tinqin.bff.api.operation.invoice.InvoiceOperationRequest;
import com.example.tinqin.bff.api.operation.invoice.InvoiceOperationResponse;
import com.example.tinqin.bff.api.operation.invoice.InvoiceTemplateDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceCreateOperationProcessor implements InvoiceConvertOperation {

    private final InvoiceConverter invoiceConverter;
    private final InvoiceHtmlGenerator invoiceHtmlGenerator;
    private final InvoicePdfGenerator invoicePdfGenerator;
    @Autowired
    public InvoiceCreateOperationProcessor(InvoiceConverter invoiceConverter, InvoiceHtmlGenerator invoiceHtmlGenerator, InvoicePdfGenerator invoicePdfGenerator) {
        this.invoiceConverter = invoiceConverter;
        this.invoiceHtmlGenerator = invoiceHtmlGenerator;
        this.invoicePdfGenerator = invoicePdfGenerator;
    }

    @Override
    public InvoiceOperationResponse process(InvoiceOperationRequest operationRequest) {
        InvoiceTemplateDataInput convert = invoiceConverter.convert(operationRequest);
        String htmlFile= invoiceHtmlGenerator.generateInvoiceHTML(convert);

        return invoicePdfGenerator.convertHtmlToPdf(htmlFile);
    }
}
