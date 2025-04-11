package com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.auth;

import com.fieldmanagement.fieldmanagement_be.common.base.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetPasswordRequest {
    @NotBlank(message = "valid.password.notBlank")
    @ValidPassword(message = "valid.password.principle")
    private String password;
    @NotBlank
    private String token;
}
