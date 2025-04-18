package com.fieldmanagement.fieldmanagement_be.booking.domain.model;

public enum BookingStatusEnum {
    PENDING(0),AWAITING_PAYMENT(1),
    PAID(2), CONFIRMED(3), IN_PROGRESS(4),
    COMPLETED(5), CANCELED(6), REFUNDED(7);

    public final int value;

    BookingStatusEnum(int value) {
        this.value = value;
    }

    public static BookingStatusEnum fromValue(int value) {
        for (BookingStatusEnum bookingStatusEnum : BookingStatusEnum.values()) {
            if (bookingStatusEnum.value == value) {
                return bookingStatusEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Booking Status Enum value: " + value);
    }
}
