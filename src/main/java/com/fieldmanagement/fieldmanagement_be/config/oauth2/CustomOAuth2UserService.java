package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import com.fieldmanagement.fieldmanagement_be.user.domain.model.ProviderEnum;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.UserDetail;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepo;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId, oAuth2User.getAttributes());

        if (oAuth2User.getAttributes().get("email") == null) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_not_found"),
                    "Auth 2.0 không cung cấp email. Vui lòng dùng cách đăng nhập khác."
            );
        }

        User userModel = userRepo.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> {
                    User user = User.builder()
                            .email(oAuth2UserInfo.getEmail())
                            .provider(ProviderEnum.valueOf(registrationId.toUpperCase()))
                            .role(RoleEnum.USER)
                            .active(true)
                            .build();

                    UserDetail userDetail = UserDetail.builder()
                            .firstName(oAuth2UserInfo.getFirstname())
                            .lastName(oAuth2UserInfo.getLastname())
                            .phoneNumber(oAuth2UserInfo.getPhoneNumber())
                            .build();
                    user.setUserDetail(userDetail);
                    return userRepo.save(user);
                });

        return new CustomOAuth2User(userModel, oAuth2User.getAttributes());
    }
}