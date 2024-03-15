package com.verifit.verifit.auth.domain.entity;

import com.verifit.verifit.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth2 {

    @Id @GeneratedValue
    private Long Id;
    @NotBlank(message = "provider는 필수입니다.")
    private String provider;
    @NotBlank(message = "provider id는 필수입니다.")
    private String providerId;
    @OneToOne
    private Member member;

    public static Oauth2 createFirstLogin(String provider, String providerId){
        return Oauth2.builder()
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public boolean checkMemberRegistration(){
        return this.member != null;
    }

    @Builder
    public Oauth2(String provider, String providerId, Member member) {
        this.provider = provider;
        this.providerId = providerId;
        this.member = member;
    }
}
