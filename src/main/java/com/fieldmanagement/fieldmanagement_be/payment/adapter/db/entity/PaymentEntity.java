package com.fieldmanagement.fieldmanagement_be.payment.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Payment")
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "amount", nullable = false)
    Float amount;

    @Column(name = "payment_time", nullable = false)
    OffsetDateTime paymentTime;

    @Column(name = "vnp_txn_ref")
    String vnpTxnRef;

    @Column(name = "note")
    String note;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_method", nullable = false)
    PaymentMethodEnum paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_type", nullable = false)
    PaymentTypeEnum paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, updatable = false)
    BookingEntity booking;
}
