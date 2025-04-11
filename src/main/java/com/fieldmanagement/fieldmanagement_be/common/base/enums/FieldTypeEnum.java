package com.fieldmanagement.fieldmanagement_be.common.base.enums;

public enum FieldTypeEnum {
    FIELD_5(0), FIELD_7(1), FIELD_9(2), FIELD_11(3);

    public final int value;

    FieldTypeEnum(int value) {
        this.value = value;
    }

    public static FieldTypeEnum fromValue(int value) {
        for (FieldTypeEnum fieldTypeEnum : FieldTypeEnum.values()) {
            if (fieldTypeEnum.value == value) {
                return fieldTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Field Type Enum value: " + value);
    }
}
