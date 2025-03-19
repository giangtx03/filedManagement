package com.fieldmanagement.commom.model.enums;

public enum PaymentTypeEnum {
    PAY(0), DEPOSIT(1), REFUNDED(2);

    private final int value;

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
