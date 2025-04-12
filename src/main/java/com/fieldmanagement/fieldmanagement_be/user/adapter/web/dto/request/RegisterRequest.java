package com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.annotation.ValidBirthDate;
import com.fieldmanagement.fieldmanagement_be.common.base.annotation.ValidEmail;
import com.fieldmanagement.fieldmanagement_be.common.base.annotation.ValidPassword;
import com.fieldmanagement.fieldmanagement_be.common.base.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "valid.email.notBlank")
    @ValidEmail(message = "valid.email.principle")
    private String email;

    @NotBlank(message = "valid.password.notBlank")
    @ValidPassword(message = "valid.password.principle")
    private String password;

    @NotBlank(message = "valid.firstName.notBlank")
    private String firstName;

    @NotBlank(message = "valid.lastName.notBlank")
    private String lastName;

    @NotBlank(message = "valid.phoneNumber.notBlank")
    @ValidPhoneNumber(message = "valid.phoneNumber.principle")
    private String phoneNumber;

    @NotNull(message = "valid.birthDate.notNull")
    @ValidBirthDate(message = "valid.birthDate.principle")
    private LocalDate dob;
}
