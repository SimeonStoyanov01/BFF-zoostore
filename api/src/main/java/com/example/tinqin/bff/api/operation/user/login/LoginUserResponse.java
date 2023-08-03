package com.example.tinqin.bff.api.operation.user.login;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponse implements OperationResponse {
    private String token;
}
