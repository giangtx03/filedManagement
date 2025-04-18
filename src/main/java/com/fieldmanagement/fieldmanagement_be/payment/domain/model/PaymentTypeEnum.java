package com.fieldmanagement.fieldmanagement_be.payment.domain.model;

public enum PaymentTypeEnum {
    PAY(0), DEPOSIT(1), REFUNDED(2);

    public final int value;

    PaymentTypeEnum(int value) {
        this.value = value;
    }

    public static PaymentTypeEnum fromValue(int value) {
        for (PaymentTypeEnum paymentTypeEnum : PaymentTypeEnum.values()) {
            if (paymentTypeEnum.value == value) {
                return paymentTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Payment Type Enum value: " + value);
    }
}
