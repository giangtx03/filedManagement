package com.fieldmanagement.fieldmanagement_be.payment.usecase;

import com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway.PaymentStrategy;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway.PaymentStrategyFactory;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request.CreatePaymentRequest;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayUseCase {
    private final PaymentStrategyFactory strategyFactory;

    public String createPayment(CreatePaymentRequest request, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        PaymentStrategy paymentStrategy = strategyFactory.getPaymentStrategy(request.getPaymentMethod());
        return paymentStrategy.processPayment(request, httpServletRequest);
    }

    public void callback(Map<String, String> params) {
        PaymentStrategy paymentStrategy = strategyFactory.getPaymentStrategy(PaymentMethodEnum.VNPAY);
        Payment payment = paymentStrategy.processCallback(params);
        log.info("Payment : {}", payment.toString());
    }
}
