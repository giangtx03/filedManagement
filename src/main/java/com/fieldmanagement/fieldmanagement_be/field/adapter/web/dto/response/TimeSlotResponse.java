package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

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
public class TimeSlotResponse extends HourlyRateResponse {
    private Boolean booked;
}
