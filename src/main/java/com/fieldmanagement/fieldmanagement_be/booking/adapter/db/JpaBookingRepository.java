package com.fieldmanagement.fieldmanagement_be.booking.adapter.db;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO(
                b,
                f
            )
            FROM Booking b
            LEFT JOIN Field f ON b.subField.field.id = f.id
            WHERE b.id = :id
            """)
    Optional<BookingEntityDTO> findDtoById(String id);
}
