package com.king.app.common.oauth2.userinfo;

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();

    String getAgeRange();

    String getGender();

    String getPhone();
}
