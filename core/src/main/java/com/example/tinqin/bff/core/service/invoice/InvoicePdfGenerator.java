package com.example.tinqin.bff.core.service.invoice;


import com.example.tinqin.bff.api.operation.invoice.InvoiceOperationResponse;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoicePdfGenerator {


    public InvoiceOperationResponse convertHtmlToPdf(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(htmlContent, outputStream, properties);
            return InvoiceOperationResponse
                    .builder()
                    .pdfFile(outputStream.toByteArray())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
