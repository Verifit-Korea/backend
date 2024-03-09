package com.verifit.verifit.client.oauth2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;


@RequiredArgsConstructor
@Getter
public enum Oauth2UserInfo {

    KAKAO("kakao") {
        @Override
        public String getProviderId() {
            return String.valueOf(this.getUserAttributes().get("id"));
        }
    },
    GOOGLE("google"){
        @Override
        public String getProviderId() {
            return String.valueOf(this.getUserAttributes().get("sub"));
        }
    }

    ;

    private final String provider;
    private Map<String, Object> userAttributes;

    public abstract String getProviderId();

    public static Oauth2UserInfo createOauth2UserInfo(String provider, Map<String, Object> userAttributes){
        Oauth2UserInfo oauth2UserInfo = Arrays.stream(Oauth2UserInfo.values())
                .filter(userInfo -> userInfo.provider.equals(provider))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("not supported oauth2 provider"));
        oauth2UserInfo.userAttributes = userAttributes;
        return oauth2UserInfo;
    }

}
