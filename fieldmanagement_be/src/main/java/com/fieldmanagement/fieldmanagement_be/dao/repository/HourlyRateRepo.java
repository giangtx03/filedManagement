package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.HourlyRateModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyRateRepo extends JpaRepository<HourlyRateModel, String> {
}
