package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.SmallFieldModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallFieldRepo extends JpaRepository<SmallFieldModel, String> {
}
