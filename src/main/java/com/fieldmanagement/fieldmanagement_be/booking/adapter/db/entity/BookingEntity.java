package com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.BookingStatusEnum;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.HourlyRateEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.SubFieldEntity;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.db.entity.PaymentEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "BookingModel")
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "note")
    String note;

    @Column(name = "booking_date", updatable = false)
    LocalDate bookingDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    BookingStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hourly_rate_id", nullable = false, updatable = false)
    HourlyRateEntity hourlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_field_id", nullable = false, updatable = false)
    SubFieldEntity subField;

    @OneToMany(mappedBy = "booking")
    List<PaymentEntity> payments;
}
