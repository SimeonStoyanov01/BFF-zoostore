package com.example.tinqin.bff.api.operation.user.login;

import com.example.tinqin.bff.api.base.OperationRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest implements OperationRequest {
    private String email;
    private String password;
}
