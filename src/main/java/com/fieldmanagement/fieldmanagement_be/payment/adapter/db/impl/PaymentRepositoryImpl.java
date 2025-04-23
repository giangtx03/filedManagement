package com.fieldmanagement.fieldmanagement_be.payment.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.db.JpaPaymentRepository;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.db.entity.PaymentEntity;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.mapper.PaymentMapper;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import com.fieldmanagement.fieldmanagement_be.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("jpaPaymentImpl")
@RequiredArgsConstructor
@Primary
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = paymentMapper.toPaymentEntity(payment);
        PaymentEntity saved = jpaPaymentRepository.save(paymentEntity);
        return paymentMapper.toPayment(saved);
    }

    @Override
    public Page<Payment> findAllOfUserWithFilter(
            String keyword, String userId,
            PaymentTypeEnum paymentType, PaymentMethodEnum paymentMethod,
            LocalDate fromDate, LocalDate toDate, Pageable pageable
    ) {
        return jpaPaymentRepository.findAllOfUserWithFilter(
                    keyword, userId, paymentType, paymentMethod,
                    fromDate, toDate, pageable
                )
                .map(paymentMapper::toPayment);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return jpaPaymentRepository.findById(id)
                .map(paymentMapper::toPayment);
    }
}
