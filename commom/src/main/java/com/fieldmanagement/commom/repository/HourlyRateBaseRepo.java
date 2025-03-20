package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.HourlyRateBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyRateBaseRepo extends JpaRepository<HourlyRateBaseModel, String> {
}
