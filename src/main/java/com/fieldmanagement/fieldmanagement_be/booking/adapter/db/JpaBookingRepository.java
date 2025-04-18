package com.fieldmanagement.fieldmanagement_be.booking.adapter.db;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.BookingStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO(
                b,
                f
            )
            FROM Booking b
            LEFT JOIN Field f ON b.subField.field.id = f.id
            WHERE b.user.id = :id
                AND (:keyword IS NULL OR :keyword = "" OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
                AND (:status IS NULL OR b.status = :status)
                AND (:fromDate IS NULL OR b.bookingDate >= :fromDate)
                AND (:toDate IS NULL OR b.bookingDate <= :toDate)
            """)
    Page<BookingEntityDTO> findAllByUserWithFilter(
            @Param("keyword") String keyword,
            @Param("id") String id,
            @Param("status") BookingStatusEnum status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate, Pageable pageable);
}
