package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.UserBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBaseRepo extends JpaRepository<UserBaseModel, String> {
}
