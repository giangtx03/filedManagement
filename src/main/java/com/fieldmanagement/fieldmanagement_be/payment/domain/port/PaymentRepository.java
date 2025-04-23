package com.fieldmanagement.fieldmanagement_be.payment.domain.port;

import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Page<Payment> findAllOfUserWithFilter(
            String keyword, String userId,
            PaymentTypeEnum paymentType, PaymentMethodEnum paymentMethod,
            LocalDate fromDate, LocalDate toDate, Pageable pageable
    );
    Optional<Payment> findById(String id);
}
