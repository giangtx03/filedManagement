package com.fieldmanagement.commom.model.enums;

public enum ProviderEnum {
    LOCAL_SYSTEM(0), GOOGLE(1), FACEBOOK(2), GITHUB(3);

    public final int value;

    ProviderEnum(int value) {
        this.value = value;
    }

    public static ProviderEnum fromValue(int value) {
        for (ProviderEnum providerEnum : ProviderEnum.values()) {
            if (providerEnum.value == value) {
                return providerEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Provider Enum value: " + value);
    }
}
