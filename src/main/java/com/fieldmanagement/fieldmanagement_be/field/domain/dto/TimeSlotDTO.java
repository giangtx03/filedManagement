package com.fieldmanagement.fieldmanagement_be.field.domain.dto;

import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TimeSlotDTO extends HourlyRate {
    private Boolean booked;
}
