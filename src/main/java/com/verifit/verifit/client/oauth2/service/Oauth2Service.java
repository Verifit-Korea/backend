package com.verifit.verifit.client.oauth2.service;

import com.verifit.verifit.client.oauth2.config.Oauth2Provider;
import com.verifit.verifit.client.oauth2.config.Oauth2ProviderFactory;
import com.verifit.verifit.client.oauth2.domain.Oauth2TokenDto;
import com.verifit.verifit.client.oauth2.domain.Oauth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final Oauth2ProviderFactory factory;
    private final String OAUTH2_GRANT_TYPE = "authorization_code";

    public Oauth2UserInfo getUserResource(String providerName, String code) {
        Oauth2Provider provider = factory.getByProviderName(providerName);

        Oauth2TokenDto tokenDto = getToken(provider, code);

        return getUserProfile(providerName, provider, tokenDto);
    }

    private Oauth2UserInfo getUserProfile(String provider, Oauth2Provider oauth2Provider, Oauth2TokenDto tokenDto) {
        Map<String, Object> userAttributes = getUserAttributes(oauth2Provider, tokenDto);
        return Oauth2UserInfo.createOauth2UserInfo(provider, userAttributes);
    }

    private Map<String, Object> getUserAttributes(Oauth2Provider provider, Oauth2TokenDto tokenDto) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUri())
                .headers(header -> header.setBearerAuth(tokenDto.accessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private Oauth2TokenDto getToken(Oauth2Provider provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUri())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(createTokenRequest(code, provider))
                .retrieve()
                .bodyToMono(Oauth2TokenDto.class)
                .block();
    }

    private MultiValueMap<String, String> createTokenRequest(String code, Oauth2Provider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("grant_type", OAUTH2_GRANT_TYPE);
        formData.add("redirect_uri", provider.getRedirectUri());
        return formData;
    }
}
