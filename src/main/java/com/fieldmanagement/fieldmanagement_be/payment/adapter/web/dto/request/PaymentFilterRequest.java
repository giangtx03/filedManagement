package com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.SearchRequest;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFilterRequest extends SearchRequest {
    private PaymentTypeEnum paymentType;
    private PaymentMethodEnum paymentMethod;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate toDate;
}
