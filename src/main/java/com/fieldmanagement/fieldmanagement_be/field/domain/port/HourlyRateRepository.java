package com.fieldmanagement.fieldmanagement_be.field.domain.port;

import com.fieldmanagement.fieldmanagement_be.field.domain.dto.TimeSlotDTO;

import java.time.LocalDate;
import java.util.List;

public interface HourlyRateRepository {
    List<TimeSlotDTO> getSchedulesBySubFieldAndDate(String subFieldId, LocalDate date);
}
