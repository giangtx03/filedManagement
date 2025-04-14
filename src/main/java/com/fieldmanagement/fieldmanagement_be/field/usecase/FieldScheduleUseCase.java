package com.fieldmanagement.fieldmanagement_be.field.usecase;

import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.HourlyRateMapper;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.ScheduleResponse;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.TimeSlotResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.TimeSlotDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.HourlyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldScheduleUseCase {
    private final HourlyRateRepository hourlyRateRepository;
    private final HourlyRateMapper hourlyRateMapper;

    public List<ScheduleResponse> getSchedules(
            String subFieldId, LocalDate startDate, LocalDate endDate
    ) {
        List<ScheduleResponse> responses = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            List<TimeSlotResponse> slots = hourlyRateRepository.getSchedulesBySubFieldAndDate(subFieldId, current)
                    .stream()
                    .map(hourlyRateMapper::toTimeSlotResponse)
                    .toList();
            ScheduleResponse schedule = ScheduleResponse.builder()
                    .date(current)
                    .timeSlots(slots)
                    .build();

            responses.add(schedule);
            current = current.plusDays(1);
        }

        return responses;
    }
}
