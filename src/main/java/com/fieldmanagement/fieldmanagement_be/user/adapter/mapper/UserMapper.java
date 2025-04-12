package com.fieldmanagement.fieldmanagement_be.user.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ProviderEnum;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserDetailEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.UpdateProfileRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.UserDetail;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {RoleEnum.class, ProviderEnum.class})
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "userDetail.phoneNumber", target = "phoneNumber")
    @Mapping(source = "userDetail.fullName", target = "fullName")
    @Mapping(source = "userDetail.avatar", target = "avatar")
    @Mapping(source = "userDetail.address", target = "address")
    @Mapping(source = "userDetail.dob", target = "dob")
    @Mapping(source = "userDetail.createdAt", target = "createdAt")
    @Mapping(source = "userDetail.updatedAt", target = "updatedAt")
    @Mapping(source = "userDetail.deletedAt", target = "deletedAt")
    UserResponse toResponse(User user);

    @Mapping(target = "role", expression = "java(RoleEnum.USER)")
    @Mapping(target = "provider", expression = "java(ProviderEnum.LOCAL_SYSTEM)")
    @Mapping(target = "password", expression =
            "java(passwordEncoder.encode(registerRequest.getPassword()))")
    User toUser(RegisterRequest registerRequest,
                     @Context PasswordEncoder passwordEncoder);

    UserDetail toUserDetail(RegisterRequest registerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    void updateUser(@MappingTarget UserDetail userDetailModel,
                    UpdateProfileRequest profileRequest);

    @Mapping(target = "userDetail.user", ignore = true)
    UserEntity toUserEntity(User user);

    @Mapping(source = "userDetail", target = "userDetail")
    User toUser(UserEntity saved);

    UserDetailEntity toUserDetailEntity(UserDetail userDetail);
}
