package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.LargeFieldModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LargeFieldRepo extends JpaRepository<LargeFieldModel, String> {
}
