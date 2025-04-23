package com.fieldmanagement.fieldmanagement_be.payment.adapter.db;

import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.db.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {

    @Query("""
            SELECT p
            FROM Payment p
            JOIN p.booking b
            JOIN b.subField sf
            JOIN sf.field f
            WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
                AND (:userId IS NULL OR b.user.id = :userId)
                AND (:paymentType IS NULL OR p.paymentType = :paymentType)
                AND (:paymentMethod IS NULL OR p.paymentMethod = :paymentMethod)
                AND (:fromDate IS NULL OR p.paymentTime >= :fromDate)
                AND (:toDate IS NULL OR p.paymentTime <= :toDate)
            """)
    Page<PaymentEntity> findAllOfUserWithFilter(
            @Param("keyword") String keyword,
            @Param("userId") String userId,
            @Param("paymentType") PaymentTypeEnum paymentType,
            @Param("paymentMethod") PaymentMethodEnum paymentMethod,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate, Pageable pageable
    );
}
