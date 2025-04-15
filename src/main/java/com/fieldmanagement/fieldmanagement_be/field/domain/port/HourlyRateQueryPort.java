package com.fieldmanagement.fieldmanagement_be.field.domain.port;

import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;

import java.util.Optional;

public interface HourlyRateQueryPort {
    Optional<HourlyRate> findById(String id);
}
