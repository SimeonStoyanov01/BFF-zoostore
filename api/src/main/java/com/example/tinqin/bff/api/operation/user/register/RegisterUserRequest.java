package com.example.tinqin.bff.api.operation.user.register;

import com.example.tinqin.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest implements OperationRequest {
    @NotBlank(message = "First name field is empty")
    private String firstName;
    @NotBlank(message = "Last name field is empty")
    private String lastName;
    @NotBlank(message = "Email field is empty")
    private String email;
    @NotBlank(message = "Password field is empty")
    private String password;

}
