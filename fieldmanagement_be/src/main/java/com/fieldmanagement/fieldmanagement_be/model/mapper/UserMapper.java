package com.fieldmanagement.fieldmanagement_be.model.mapper;

import com.fieldmanagement.commom.model.enums.ProviderEnum;
import com.fieldmanagement.commom.model.enums.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {RoleEnum.class, ProviderEnum.class})
public interface UserMapper {

    @Mapping(source = "userDetailDto", target = "fullName", qualifiedByName = "toFullName")
    UserResponse toResponse(UserDetailDto userDetailDto);

    @Mapping(source = "userDetailModel", target = "fullName", qualifiedByName = "toFullName")
    UserResponse toResponse(UserDetailModel userDetailModel);

    @Mapping(target = "role", expression = "java(RoleEnum.USER)")
    @Mapping(target = "provider", expression = "java(ProviderEnum.LOCAL_SYSTEM)")
    @Mapping(target = "password", expression =
            "java(passwordEncoder.encode(registerRequest.getPassword()))")
    UserModel toUserModel(RegisterRequest registerRequest,
                          @Context PasswordEncoder passwordEncoder);

    UserDetailModel toUserDetailModel(RegisterRequest registerRequest);

    @Named("toFullName")
    default String toFullName(UserDetailDto userDetailDto) {
        if (userDetailDto == null) return null;
        return userDetailDto.getFirstName() + " " + userDetailDto.getLastName();
    }

    @Named("toFullName")
    default String toFullName(UserDetailModel userDetailDto) {
        if (userDetailDto == null) return null;
        return userDetailDto.getFirstName() + " " + userDetailDto.getLastName();
    }
}
