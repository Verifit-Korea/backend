package com.verifit.verifit.auth.domain.response;

import com.verifit.verifit.auth.domain.entity.Oauth2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Oauth2ResponseTest {

    @DisplayName("기존 회원의 인증 응답을 생성한다.")
    @Test
    void login() {
        // given
        Oauth2 oauth2 = Oauth2.builder()
                .provider("kakao")
                .providerId("kakao id")
                .build();

        // when
        Oauth2Response response = Oauth2Response.login("access token", oauth2);

        // then
        assertThat(response.redirect()).isEqualTo("/");
        assertThat(response.accessToken()).isEqualTo("access token");
        assertThat(response.provider()).isEqualTo("kakao");
        assertThat(response.providerId()).isEqualTo("kakao id");
    }

    @DisplayName("신규 회원의 인증 응답을 생성한다.")
    @Test
    void signup() {
        // given
        Oauth2 oauth2 = Oauth2.builder()
                .provider("kakao")
                .providerId("kakao id")
                .build();

        // when
        Oauth2Response response = Oauth2Response.signup(oauth2);

        // then
        assertThat(response.redirect()).isEqualTo("/signup");
        assertThat(response.accessToken()).isNull();
        assertThat(response.provider()).isEqualTo("kakao");
        assertThat(response.providerId()).isEqualTo("kakao id");
    }
}