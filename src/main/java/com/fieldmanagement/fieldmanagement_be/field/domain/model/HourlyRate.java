package com.fieldmanagement.fieldmanagement_be.field.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HourlyRate extends BaseModel {
    String id;
    LocalTime startTime;
    LocalTime endTime;
    Float price;
}
