package com.fieldmanagement.fieldmanagement_be.model.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Parameter(example = "user@gmail.com")
    @NotBlank
    private String email;


    @Parameter(example = "user123")
    @NotBlank
    private String password;
}
