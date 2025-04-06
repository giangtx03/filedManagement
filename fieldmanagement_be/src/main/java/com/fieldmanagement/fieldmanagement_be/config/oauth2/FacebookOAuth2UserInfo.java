package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import java.util.Map;

public class FacebookOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getFirstname() {
        return  (String) attributes.get("first_name");
    }

    @Override
    public String getLastname() {
       return  (String) attributes.get("last_name");
    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("phone_number");
    }
}

