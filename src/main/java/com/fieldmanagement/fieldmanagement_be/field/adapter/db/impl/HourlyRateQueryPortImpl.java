package com.fieldmanagement.fieldmanagement_be.field.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.JpaHourlyRateRepository;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.HourlyRateMapper;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.HourlyRateQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class HourlyRateQueryPortImpl implements HourlyRateQueryPort {
    private final JpaHourlyRateRepository jpaHourlyRateRepository;
    private final HourlyRateMapper hourlyRateMapper;

    @Override
    public Optional<HourlyRate> findById(String id) {
        return jpaHourlyRateRepository.findById(id)
                .map(hourlyRateMapper::toHourlyRate);
    }
}
