package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.PaymentBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentBaseRepo extends JpaRepository<PaymentBaseModel, String> {
}
