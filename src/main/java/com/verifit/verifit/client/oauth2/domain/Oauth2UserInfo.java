package com.verifit.verifit.client.oauth2.domain;

<<<<<<< HEAD
=======
import com.verifit.verifit.global.exception.ApiException;
>>>>>>> 674d07c8399fbfdc42c878c922a14b42d6482c67
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;

import static com.verifit.verifit.global.exception.ExceptionCode.OAUTH2_PROVIDER_NOT_SUPPORTED;


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
                .orElseThrow(() -> new ApiException(OAUTH2_PROVIDER_NOT_SUPPORTED));
        oauth2UserInfo.userAttributes = userAttributes;
        return oauth2UserInfo;
    }

}
