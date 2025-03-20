package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.NotificationBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationBaseRepo extends JpaRepository<NotificationBaseModel, String> {
}
