package com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.HourlyRateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotEntityDTO extends HourlyRateEntity {
    private Boolean booked;

    public TimeSlotEntityDTO(HourlyRateEntity hourlyRate, Boolean booked) {
        setBaseHourlyRate(hourlyRate);
        this.booked = booked;
    }

    private void setBaseHourlyRate(HourlyRateEntity hourlyRate) {
        super.setId(hourlyRate.getId());
        super.setStartTime(hourlyRate.getStartTime());
        super.setEndTime(hourlyRate.getEndTime());
        super.setPrice(hourlyRate.getPrice());
        super.setCreatedAt(hourlyRate.getCreatedAt());
        super.setCreatedAt(hourlyRate.getCreatedAt());
        super.setUpdatedAt(hourlyRate.getUpdatedAt());
    }
}
