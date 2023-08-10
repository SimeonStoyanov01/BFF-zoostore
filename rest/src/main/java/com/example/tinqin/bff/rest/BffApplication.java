package com.example.tinqin.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.tinqin.bff")
@EntityScan(basePackages = "com.example.tinqin.bff.persistence.entity")
//@EntityScan(basePackages = "com.example.tinqin.bff.persistence.repository")
@EnableJpaRepositories(basePackages = "com.example.tinqin.bff")
public class BffApplication {

	public static void main(String[] args) {
		SpringApplication.run(BffApplication.class, args);
	}

}
