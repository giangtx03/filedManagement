package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.BaseResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldSimpleResponse extends BaseResponse {
    String id;
    String name;
    String location;
    String urlSlug;
}
