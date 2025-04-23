package com.fieldmanagement.fieldmanagement_be.payment.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.payment.adapter.db.entity.PaymentEntity;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentEntity toPaymentEntity(Payment payment);

    Payment toPayment(PaymentEntity saved);
}
