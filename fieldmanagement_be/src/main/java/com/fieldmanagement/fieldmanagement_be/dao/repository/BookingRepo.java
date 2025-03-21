package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<BookingModel, String> {
}
