package com.verifit.verifit.auth.domain.entity;

import com.verifit.verifit.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class Oauth2Test {
    
    @DisplayName("oauth2 정보와 member가 연동되었는지 확인한다.")
    @ParameterizedTest
    @MethodSource("provideCheckMemberRegistration")
    void checkMemberRegistration(Member member, boolean expected) {
        // given
        Oauth2 oauth2 = Oauth2.builder()
                .provider("kakao")
                .providerId("kakao id")
                .member(member)
                .build();

        // when
        boolean checkedMemberRegistration = oauth2.checkMemberRegistration();

        // then
        Assertions.assertThat(checkedMemberRegistration).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCheckMemberRegistration(){
        return Stream.of(
                Arguments.of(Member.builder().build(), true),
                Arguments.of(null, false)
        );
    }

}