package com.example.tinqin.bff.domain;

import com.example.tinqin.bff.restexport.ZooStoreStorageRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageClientFactory {
    @Bean
    ZooStoreStorageRestClient getStorageRestExportClient(){
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(ZooStoreStorageRestClient.class,"http://localhost:8081");
    }
}
