package com.fieldmanagement.fieldmanagement_be.config.oauth2;

public interface OAuth2UserInfo {
    String getEmail();
    String getFirstname();
    String getLastname();
    String getPhoneNumber();
}
