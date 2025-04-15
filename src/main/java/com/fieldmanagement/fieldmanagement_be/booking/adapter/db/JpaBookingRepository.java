package com.fieldmanagement.fieldmanagement_be.booking.adapter.db;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    @Query("""
            SELECT
                CASE WHEN COUNT(b) > 0 THEN true ELSE false END
            FROM Booking b
            WHERE b.subField.id = :subFieldId
                  AND b.hourlyRate.id = :hourlyRateId
                  AND b.bookingDate = :date
                  AND b.status IN (0, 1, 2, 3, 4 ,5)
            """)
    boolean isBookedBySubFieldAndHourlyRateAndDate(
            @Param("subFieldId") String subFieldId,
            @Param("hourlyRateId") String hourlyRateId,
            @Param("date") LocalDate date);
}
