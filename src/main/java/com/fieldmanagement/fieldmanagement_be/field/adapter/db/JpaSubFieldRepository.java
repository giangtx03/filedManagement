package com.fieldmanagement.fieldmanagement_be.field.adapter.db;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.SubFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubFieldRepository extends JpaRepository<SubFieldEntity, String> {
}
