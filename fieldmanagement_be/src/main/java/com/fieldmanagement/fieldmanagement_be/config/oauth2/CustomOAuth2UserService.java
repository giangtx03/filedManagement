package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import com.fieldmanagement.commom.model.enums.ProviderEnum;
import com.fieldmanagement.commom.model.enums.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserDetailRepo;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId, oAuth2User.getAttributes());

        UserModel userModel = userRepo.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> {
                    UserModel user = UserModel.builder()
                            .email(oAuth2UserInfo.getEmail())
                            .provider(ProviderEnum.valueOf(registrationId.toUpperCase()))
                            .role(RoleEnum.USER)
                            .isActive(true)
                            .build();

                    UserModel saveUser = userRepo.save(user);

                    UserDetailModel userDetailModel = UserDetailModel.builder()
                            .firstName(oAuth2UserInfo.getFirstname())
                            .lastName(oAuth2UserInfo.getLastname())
                            .phoneNumber(oAuth2UserInfo.getPhoneNumber())
                            .user(saveUser)
                            .build();

                    userDetailRepo.save(userDetailModel);
                    return saveUser;
                });

        return new CustomOAuth2User(userModel, oAuth2User.getAttributes());
    }
}