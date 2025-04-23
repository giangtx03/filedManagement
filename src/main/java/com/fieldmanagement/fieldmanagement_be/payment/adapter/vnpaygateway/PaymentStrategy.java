package com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway;

import com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request.CreatePaymentRequest;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PaymentStrategy {
    String processPayment(CreatePaymentRequest request, HttpServletRequest httpRequest)
            throws UnsupportedEncodingException;
    Payment processCallback(Map<String, String> params);
    void processRefund(CreatePaymentRequest request);
}
