package com.fieldmanagement.commom.model.enums;

public enum PaymentMethodEnum {
    CASH(0), VNPAY(1), CREDIT(2);

    private final int value;

    PaymentMethodEnum(int value) {
        this.value = value;
    }

    public static PaymentMethodEnum fromValue(int value) {
        for (PaymentMethodEnum paymentMethodEnum : PaymentMethodEnum.values()) {
            if (paymentMethodEnum.value == value) {
                return paymentMethodEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Payment Method Enum value: " + value);
    }
}
