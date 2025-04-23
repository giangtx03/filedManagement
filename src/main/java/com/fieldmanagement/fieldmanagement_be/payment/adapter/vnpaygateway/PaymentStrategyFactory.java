package com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway;

import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {
    private final Map<String, PaymentStrategy> strategies;

    public PaymentStrategy getPaymentStrategy(PaymentMethodEnum paymentMethod) {
        return Optional.ofNullable(strategies.get(paymentMethod.name().toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Phương thức thanh toán không được hỗ trợ" + paymentMethod));
    }
}
