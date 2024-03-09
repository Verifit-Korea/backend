package com.verifit.verifit.auth.domain.response;

public record Oauth2Response(
        String redirect,
        String accessToken
) {
}
