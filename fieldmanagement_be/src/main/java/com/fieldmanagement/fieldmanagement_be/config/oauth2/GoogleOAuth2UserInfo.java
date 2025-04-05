package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getFirstname() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getLastname() {
        return (String) attributes.get("family_name");
    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("phone_number");
    }
}
