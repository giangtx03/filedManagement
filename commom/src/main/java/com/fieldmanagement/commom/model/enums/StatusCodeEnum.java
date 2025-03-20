package com.fieldmanagement.commom.model.enums;

public enum StatusCodeEnum {

    SUCCESS200(1000),
    FAILED404(1001);

    public final int value;

    StatusCodeEnum(int value) {
        this.value = value;
    }
}
