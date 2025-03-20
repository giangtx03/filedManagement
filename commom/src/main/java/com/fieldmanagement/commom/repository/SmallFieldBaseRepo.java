package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.SmallFieldBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallFieldBaseRepo extends JpaRepository<SmallFieldBaseModel, String> {
}
