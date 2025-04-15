package com.fieldmanagement.fieldmanagement_be.field.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.FieldTypeEnum;
import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
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
public class SubField extends BaseModel {
    String id;
    String name;
    FieldTypeEnum type;

    List<HourlyRate> hourlyRates;
}
