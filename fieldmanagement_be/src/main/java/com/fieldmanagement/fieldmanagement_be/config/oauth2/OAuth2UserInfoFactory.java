package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(
            String registrationId, Map<String, Object> attributes
    ) {
        switch (registrationId.toLowerCase()) {
            case "google":
                return new GoogleOAuth2UserInfo(attributes);
            case "facebook":
                return new FacebookOAuth2UserInfo(attributes);
            default:
                throw new OAuth2AuthenticationException(
                        "Nhà cung cấp OAuth2 không được hỗ trợ: " + registrationId);
        }
    }
}
