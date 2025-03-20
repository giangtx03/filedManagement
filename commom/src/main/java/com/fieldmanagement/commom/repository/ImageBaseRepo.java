package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.ImageBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageBaseRepo extends JpaRepository<ImageBaseModel, String> {
}
