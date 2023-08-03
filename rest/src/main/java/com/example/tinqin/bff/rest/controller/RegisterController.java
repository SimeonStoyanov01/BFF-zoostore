package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.user.register.RegisterUserOperation;
import com.example.tinqin.bff.api.operation.user.register.RegisterUserRequest;
import com.example.tinqin.bff.api.operation.user.register.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegisterController {
    private final RegisterUserOperation registerUserOperation;
    @Autowired
    public RegisterController(RegisterUserOperation registerUserOperation) {
        this.registerUserOperation = registerUserOperation;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register
            (@RequestParam(name = "email") String email,
             @RequestParam(name = "firstName")String firstName,
             @RequestParam(name = "lastName")String lastName,
             @RequestParam(name = "password")String password){
        RegisterUserRequest registerUser=RegisterUserRequest
                .builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();
        return ResponseEntity.ok(registerUserOperation.process(registerUser));
    }
}
