package com.fieldmanagement.fieldmanagement_be.user.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ProviderEnum;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class User extends BaseModel {
    String id;
    String email;
    String password;
    boolean locked;
    boolean active;
    RoleEnum role;
    ProviderEnum provider;
    UserDetail userDetail;
}
