package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.ReviewBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBaseRepo extends JpaRepository<ReviewBaseModel, String> {
}
