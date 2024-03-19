package com.verifit.verifit.auth.service;


import com.verifit.verifit.auth.domain.entity.Oauth2;
import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.auth.repository.Oauth2Repository;
import com.verifit.verifit.client.oauth2.domain.Oauth2UserInfo;
import com.verifit.verifit.client.oauth2.service.Oauth2Service;
import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.global.jwt.JwtProvider;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
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
    private final MemberService memberService;
    private final Oauth2Repository oauth2Repository;

    @Transactional
    public Oauth2Response authenticate(String provider, String code) {
        Oauth2UserInfo userInfo = oauth2Service.getUserResource(provider, code);

        Oauth2 oauth2 = getOauth2ByProviderInfo(userInfo.getProvider(), userInfo.getProviderId());

        return getOauth2Response(oauth2);
    }

    private Oauth2 getOauth2ByProviderInfo(String provider, String providerId) {
        return oauth2Repository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> oauth2Repository.save(Oauth2.createFirstLogin(provider, providerId)));
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

    @Transactional
    public void registerUsingPassword(String email, String password, String nickname) {
        if (memberService.isEmailAlreadyExists(email)) {
            throw new ApiException(ExceptionCode.EMAIL_IS_ALREADY_IN_USE);
        }
        if (memberService.isNicknameAlreadyExists(nickname)) {
            throw new ApiException(ExceptionCode.NICKNAME_IS_ALREADY_IN_USE);
        }
        createNewMember(email, password, nickname);
    }

    private void createNewMember(String email, String password, String nickname) {
        Member member = Member.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .nickname(nickname)
            .profileUrl(Member.DEFAULT_PROFILE_URL)
            .build();
        memberRepository.save(member);
    }

    private String getRedirect(Member member){
        return StringUtils.hasText(member.getNickname()) ? "/signup" : "/";
    }
      
    private Oauth2Response getOauth2Response(Oauth2 oauth2) {
        if (oauth2.checkMemberRegistration()) {
            return Oauth2Response.login(jwtProvider.generateAccessToken(oauth2.getMember()), oauth2);
        } else {
            return Oauth2Response.signup(oauth2);
        }
    }
}
