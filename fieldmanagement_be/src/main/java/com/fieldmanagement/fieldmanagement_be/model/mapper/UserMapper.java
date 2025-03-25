package com.fieldmanagement.fieldmanagement_be.model.mapper;

import com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userDetailDto", target = "fullName", qualifiedByName = "toFullName")
    UserResponse toResponse(UserDetailDto userDetailDto);

    @Named("toFullName")
    default String toFullName(UserDetailDto userDetailDto) {
        if (userDetailDto == null) return null;
        return userDetailDto.getFirstName() + " " + userDetailDto.getLastName();
    }
}
