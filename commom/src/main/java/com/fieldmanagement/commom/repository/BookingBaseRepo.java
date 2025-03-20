package com.fieldmanagement.commom.repository;

import com.fieldmanagement.commom.model.entity.BookingBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingBaseRepo extends JpaRepository<BookingBaseModel, String> {
}
