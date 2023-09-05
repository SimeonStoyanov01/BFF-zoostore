package com.example.tinqin.bff.core.service.invoice;

import com.example.tinqin.bff.api.operation.invoice.InvoiceTemplateDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class InvoiceHtmlGenerator {


    private ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }


    private SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    public String generateInvoiceHTML(InvoiceTemplateDataInput dataInput) {

        Timestamp timeOfSale = dataInput.getTimeOfSale();
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedTime = targetFormat.format(timeOfSale);

        Context context = new Context();
        context.setVariable("userId", dataInput.getUserId());
        context.setVariable("firstName", dataInput.getFirstName());
        context.setVariable("lastName", dataInput.getLastName());
        context.setVariable("invoiceItems", dataInput.getInvoiceItems());
        context.setVariable("bill", dataInput.getBill());
        context.setVariable("timeOfSale", formattedTime);

        return templateEngine().process("invoice_template", context);
    }


}
