package com.fieldmanagement.fieldmanagement_be.field.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.FieldTypeEnum;
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
public class SubField {
    String id;
    String name;
    FieldTypeEnum type;

    List<HourlyRate> hourlyRates;
}
