package com.fieldmanagement.commom.model.enums;

public enum RoleEnum {
    ADMIN(0), MANAGER(1), USER(2);

    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }

    public static RoleEnum fromValue(int value) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.value == value) {
                return roleEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Role Enum value: " + value);
    }
}
