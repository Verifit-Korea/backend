package com.verifit.verifit.auth.service;

import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.client.oauth2.domain.Oauth2UserInfo;
import com.verifit.verifit.client.oauth2.service.Oauth2Service;
import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.global.jwt.JwtProvider;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public String loginUsingPassword(String email, String password) {
        Member member = findMemberByEmail(email);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return jwtProvider.generateAccessToken(member);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private String getRedirect(Member member){
        return StringUtils.hasText(member.getNickname()) ? "/signup" : "/";
    }
}
