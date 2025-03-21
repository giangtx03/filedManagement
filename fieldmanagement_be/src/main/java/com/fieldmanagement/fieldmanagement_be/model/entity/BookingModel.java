package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.BookingBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "BookingModel")
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingModel extends BookingBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hourly_rate_id", nullable = false, updatable = false)
    HourlyRateModel hourlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "small_field_id", nullable = false, updatable = false)
    SmallFieldModel smallField;

    @OneToMany(mappedBy = "booking")
    List<PaymentModel> payments;
}
