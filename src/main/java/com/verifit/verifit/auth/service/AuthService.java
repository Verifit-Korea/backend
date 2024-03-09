package com.verifit.verifit.auth.service;

import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.client.oauth2.domain.Oauth2UserInfo;
import com.verifit.verifit.client.oauth2.service.Oauth2Service;
import com.verifit.verifit.global.jwt.JwtProvider;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final Oauth2Service oauth2Service;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public Oauth2Response authenticate(String provider, String code) {
        Oauth2UserInfo userInfo = oauth2Service.getUserResource(provider, code);

        Member member = findOauth2Member(userInfo.getProvider(), userInfo.getProviderId());

        return new Oauth2Response(getRedirect(member), jwtProvider.generateAccessToken(member));
    }

    private Member findOauth2Member(String provider, String providerId) {
        return memberRepository
                .findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> memberRepository.save(Member.createOauthMember(provider, providerId)));
    }

    private String getRedirect(Member member){
        return StringUtils.hasText(member.getNickname()) ? "/signup" : "/";
    }
}
