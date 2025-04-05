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
        String name = (String) attributes.get("name");
        return (name != null && name.contains(" ")) ? name.split(" ")[0] : name;
    }

    @Override
    public String getLastname() {
        String name = (String) attributes.get("name");
        return (name != null && name.contains(" ")) ? name.split(" ")[1] : "";
    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("phone_number");
    }
}

