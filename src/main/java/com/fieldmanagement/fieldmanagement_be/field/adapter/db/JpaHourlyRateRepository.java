package com.fieldmanagement.fieldmanagement_be.field.adapter.db;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.TimeSlotEntityDTO;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.HourlyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaHourlyRateRepository extends JpaRepository<HourlyRateEntity, String> {
    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.TimeSlotEntityDTO(
                hr,
                CASE
                    WHEN COUNT(b) > 0 THEN true
                    ELSE false
                END
            )
            FROM HourlyRate hr
            JOIN hr.subFields sf
            LEFT JOIN Booking b ON b.hourlyRate.id = hr.id AND b.bookingDate = :date
            WHERE sf.id = :subFieldId
            GROUP BY hr
            """)
    List<TimeSlotEntityDTO> findSchedulesBySubFieldAndDate(
            @Param("subFieldId") String subFieldId,
            @Param("date") LocalDate date);
}
