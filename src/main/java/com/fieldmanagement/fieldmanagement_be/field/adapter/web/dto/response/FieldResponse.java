package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.config.file.ImageField;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldResponse extends FieldSimpleResponse {
    String location;
    String description;
    UserResponse owner;
    @ImageField
    List<String> images;
    Float avgStar;
}
