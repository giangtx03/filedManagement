package com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    @NotBlank
    private String bookingId;
    @NotNull
    private PaymentTypeEnum paymentType;
    @NotNull
    private PaymentMethodEnum paymentMethod;
    private String note;
}
