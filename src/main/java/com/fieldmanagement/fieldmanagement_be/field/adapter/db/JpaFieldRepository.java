package com.fieldmanagement.fieldmanagement_be.field.adapter.db;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.FieldEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface JpaFieldRepository extends JpaRepository<FieldEntity, String> {
    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.FieldEntityDTO(
                f,
                avg(coalesce(r.rating, 0))
            )
            FROM Field f
            LEFT JOIN Review r ON r.field.id = f.id
            WHERE f.urlSlug = :urlSlug
            GROUP BY f
            """)
    Optional<FieldEntityDTO> findByUrlSlug(@Param("urlSlug") String urlSlug);

    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.FieldEntityDTO(
                f,
                avg(coalesce(r.rating, 0))
            )
            FROM Field f
            LEFT JOIN Review r ON r.field.id = f.id
            LEFT JOIN f.subFields sf
            LEFT JOIN sf.hourlyRates hr
            WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
                AND (:location IS NULL OR f.location LIKE CONCAT('%', :location, '%'))
                AND (:startPrice IS NULL OR hr.price >= :startPrice)
                AND (:endPrice IS NULL OR hr.price <= :endPrice)
                AND (:startTime IS NULL OR hr.startTime >= :startTime)
                AND (:endTime IS NULL OR hr.endTime <= :endTime)
            GROUP BY f
            """)
    Page<FieldEntityDTO> findAllField(
            @Param("keyword") String keyword, @Param("location") String location,
            @Param("startPrice") Float startPrice, @Param("endPrice") Float endPrice,
            @Param("startTime") LocalTime fromTime, @Param("endTime") LocalTime toTime, Pageable pageable);
}
