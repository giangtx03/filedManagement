package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {
    private LocalDate date;
    private List<TimeSlotResponse> timeSlots;
}
