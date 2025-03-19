package com.fieldmanagement.commom.model.enums;

public enum ImageTargetTypeEnum {
    FIELD(0), USER(1), REVIEW(2);

    private final int value;

    ImageTargetTypeEnum(int value) {
        this.value = value;
    }

    public static ImageTargetTypeEnum fromValue(int value) {
        for (ImageTargetTypeEnum imageTargetTypeEnum : ImageTargetTypeEnum.values()) {
            if (imageTargetTypeEnum.value == value) {
                return imageTargetTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Image Target Type Enum value: " + value);
    }
}
