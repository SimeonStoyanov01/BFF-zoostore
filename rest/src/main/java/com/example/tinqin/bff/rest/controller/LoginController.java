package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.user.login.LoginUserOperation;
import com.example.tinqin.bff.api.operation.user.login.LoginUserRequest;
import com.example.tinqin.bff.api.operation.user.login.LoginUserResponse;
import com.example.tinqin.bff.api.operation.user.register.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final LoginUserOperation loginUserOperation;
    @Autowired
    public LoginController(LoginUserOperation loginUserOperation) {
        this.loginUserOperation = loginUserOperation;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser
            (@RequestParam(name = "email") String email,
             @RequestParam(name = "password")String password){
        LoginUserRequest loginUserRequest=
                LoginUserRequest
                        .builder()
                        .email(email)
                        .password(password)
                        .build();
        return ResponseEntity.ok(loginUserOperation.process(loginUserRequest));
    }
    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
