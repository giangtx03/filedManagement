package com.fieldmanagement.fieldmanagement_be.payment.usecase;

import com.fieldmanagement.fieldmanagement_be.payment.adapter.mapper.PaymentMapper;
import com.fieldmanagement.fieldmanagement_be.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
}
