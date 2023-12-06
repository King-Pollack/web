package com.king.app.common.oauth2.service;


import com.king.app.common.oauth2.userinfo.PrincipalDetails;
import com.king.app.common.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.king.app.domain.type.UserAgeRange;
import com.king.app.domain.type.UserGender;
import com.king.app.domain.user.User;
import com.king.app.infrastructure.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        KakaoOAuth2UserInfo kakaoOAuth2UserInfo = new KakaoOAuth2UserInfo(attributes,provider);
        User createdUser = generateUser(kakaoOAuth2UserInfo);
        return new PrincipalDetails(createdUser, attributes);
    }

    private User generateUser(KakaoOAuth2UserInfo kakaoOAuth2UserInfo) {
        return userRepository.findByProviderId(kakaoOAuth2UserInfo.getProviderId())
                .orElseGet(() ->
                        saveUserAndReturn(kakaoOAuth2UserInfo)
                );
    }

    private User saveUserAndReturn(KakaoOAuth2UserInfo kakaoOAuth2UserInfo) {
        User generateUser = User.builder()
                .provider(kakaoOAuth2UserInfo.getProvider())
                .providerId(kakaoOAuth2UserInfo.getProviderId())
                .ageRange(UserAgeRange.of(kakaoOAuth2UserInfo.getAgeRange()))
                .gender(UserGender.of(kakaoOAuth2UserInfo.getGender()))
                .email(kakaoOAuth2UserInfo.getEmail())
                .nickname(kakaoOAuth2UserInfo.getName())
                .phoneNumber(kakaoOAuth2UserInfo.getPhone())
                .build();
        try {
            return userRepository.save(generateUser);
        } catch (DuplicateKeyException e) {
            log.error("DuplicateKeyException: {}", e.getMessage());
        }
        return generateUser;
    }
}