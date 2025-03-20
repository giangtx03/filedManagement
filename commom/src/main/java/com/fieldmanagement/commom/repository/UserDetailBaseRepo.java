package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.UserDetailBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailBaseRepo extends JpaRepository<UserDetailBaseModel, String> {
}
