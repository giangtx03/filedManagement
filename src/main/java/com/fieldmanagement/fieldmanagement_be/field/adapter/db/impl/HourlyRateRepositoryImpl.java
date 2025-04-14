package com.fieldmanagement.fieldmanagement_be.field.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.JpaHourlyRateRepository;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.HourlyRateMapper;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.TimeSlotDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.HourlyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository("jpaHourlyRateImpl")
public class HourlyRateRepositoryImpl implements HourlyRateRepository {
    private final JpaHourlyRateRepository jpaHourlyRateRepository;
    private final HourlyRateMapper hourlyRateMapper;
    @Override
    public List<TimeSlotDTO> getSchedulesBySubFieldAndDate(String subFieldId, LocalDate date) {
        return jpaHourlyRateRepository.findSchedulesBySubFieldAndDate(subFieldId, date)
                .stream()
                .map(hourlyRateMapper::toTimeSlotDTO)
                .toList();
    }
}
