package com.example.tinqin.bff.api.operation.user.register;

import com.example.tinqin.bff.api.base.OperationResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse implements OperationResponse {
    private String token;
}
