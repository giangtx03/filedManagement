package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepo extends JpaRepository<UserDetailModel, String> {
}
