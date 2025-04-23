package com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway;

import com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request.CreatePaymentRequest;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("cash")
public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public String processPayment(CreatePaymentRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public Payment processCallback(Map<String, String> params) {
        return null;
    }

    @Override
    public void processRefund(CreatePaymentRequest request) {
    }
}
