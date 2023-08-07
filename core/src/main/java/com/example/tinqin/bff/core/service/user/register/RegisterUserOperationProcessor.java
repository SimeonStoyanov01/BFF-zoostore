package com.example.tinqin.bff.core.service.user.register;

import com.example.tinqin.bff.api.operation.user.register.RegisterUserOperation;
import com.example.tinqin.bff.api.operation.user.register.RegisterUserRequest;
import com.example.tinqin.bff.api.operation.user.register.RegisterUserResponse;
import com.example.tinqin.bff.core.config.JwtService;
import com.example.tinqin.bff.core.exceptions.*;
import com.example.tinqin.bff.persistence.entity.Role;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RegisterUserOperationProcessor implements RegisterUserOperation {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public RegisterUserOperationProcessor(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterUserResponse process(RegisterUserRequest operationRequest) {
        if(userRepository.findUserEntityByEmail(operationRequest.getEmail()).isPresent()){
            throw new UserExistsException();
        }
        UserEntity user=new UserEntity();
        user.setEmail(operationRequest.getEmail());
        user.setFirstName(operationRequest.getFirstName());
        user.setLastName(operationRequest.getLastName());
        user.setPassword(passwordEncoder.encode(operationRequest.getPassword()));
        user.setRole(Role.USER);
        user.setCartItems(new HashSet<>());
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return RegisterUserResponse
                .builder()
                .token(token)
                .build();
    }
}
