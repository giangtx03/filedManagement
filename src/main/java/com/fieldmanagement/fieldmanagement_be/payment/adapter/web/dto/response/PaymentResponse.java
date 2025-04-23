package com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
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
public class PaymentResponse {
    String id;
    Float amount;
    String note;
    OffsetDateTime paymentTime;
    String vnpTxnRef;
    PaymentMethodEnum paymentMethod;
    PaymentTypeEnum paymentType;
    BookingDetailResponse booking;
}
