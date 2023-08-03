package com.example.tinqin.bff.core.service.user.login;

import com.example.tinqin.bff.api.operation.user.login.LoginUserOperation;
import com.example.tinqin.bff.api.operation.user.login.LoginUserRequest;
import com.example.tinqin.bff.api.operation.user.login.LoginUserResponse;
import com.example.tinqin.bff.core.config.JwtService;
import com.example.tinqin.bff.core.exceptions.NoUserExistsException;
import com.example.tinqin.bff.persistence.entity.UserEntity;
import com.example.tinqin.bff.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserOperationProcessor implements LoginUserOperation {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public LoginUserOperationProcessor(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginUserResponse process(LoginUserRequest operationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                operationRequest.getEmail(),
                operationRequest.getPassword()
        ));
        UserEntity user = userRepository.findUserEntityByEmail(operationRequest.getEmail()).orElseThrow(NoUserExistsException::new);
        String token = jwtService.generateToken(user);


        return LoginUserResponse
                .builder()
                .token(token)
                .build();
    }
}
