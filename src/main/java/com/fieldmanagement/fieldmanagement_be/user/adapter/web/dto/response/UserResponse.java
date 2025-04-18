package com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.BaseResponse;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.config.aop.ImageField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse {
    private String id;
    private String fullName;
    private String email;
    @ImageField
    private String avatar;
    private String address;
    private LocalDate dob;
    private String phoneNumber;
    private RoleEnum role;
}
