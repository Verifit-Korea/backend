package com.verifit.verifit.auth.service;

<<<<<<< HEAD
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
=======
import com.verifit.verifit.auth.domain.entity.Oauth2;
import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.auth.repository.Oauth2Repository;
import com.verifit.verifit.client.oauth2.domain.Oauth2UserInfo;
import com.verifit.verifit.client.oauth2.service.Oauth2Service;
import com.verifit.verifit.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> 674d07c8399fbfdc42c878c922a14b42d6482c67

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final Oauth2Service oauth2Service;
    private final JwtProvider jwtProvider;
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

    private Oauth2Response getOauth2Response(Oauth2 oauth2) {
        if (oauth2.checkMemberRegistration()) {
            return Oauth2Response.login(jwtProvider.generateAccessToken(oauth2.getMember()), oauth2);
        } else {
            return Oauth2Response.signup(oauth2);
        }
    }
}
