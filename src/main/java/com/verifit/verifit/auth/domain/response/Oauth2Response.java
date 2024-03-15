package com.verifit.verifit.auth.domain.response;

import com.verifit.verifit.auth.domain.entity.Oauth2;
import lombok.Builder;

@Builder
public record Oauth2Response(
        String redirect,
        String accessToken,
        String provider,
        String providerId
) {
    public static Oauth2Response login(String accessToken, Oauth2 oauth2){
        return Oauth2Response.builder()
                .redirect("/")
                .accessToken(accessToken)
                .provider(oauth2.getProvider())
                .providerId(oauth2.getProviderId())
                .build();
    }

    public static Oauth2Response signup(Oauth2 oauth2){
        return Oauth2Response.builder()
                .redirect("/signup")
                .provider(oauth2.getProvider())
                .providerId(oauth2.getProviderId())
                .build();
    }
}
