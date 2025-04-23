package com.fieldmanagement.fieldmanagement_be.payment.domain.model;

import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    String id;
    Long amount;
    String note;
    OffsetDateTime paymentTime;
    String vnpTxnRef;
    PaymentMethodEnum paymentMethod;
    PaymentTypeEnum paymentType;
    Booking booking;
}
