package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.LargeFieldBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LargeFieldBaseRepo extends JpaRepository<LargeFieldBaseModel, String> {
}
