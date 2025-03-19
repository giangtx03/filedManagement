package com.fieldmanagement.commom.model.entity;

import com.fieldmanagement.commom.model.enums.BookingStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentBaseModel {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "amount", nullable = false)
    Float amount;

    @Column(name = "payment_date", nullable = false)
    OffsetDateTime paymentDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_method", nullable = false)
    BookingStatusEnum paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_type", nullable = false)
    BookingStatusEnum paymentType;
}
