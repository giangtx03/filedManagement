package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.FieldTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubFieldResponse {
    String id;
    String name;
    FieldTypeEnum type;
}
