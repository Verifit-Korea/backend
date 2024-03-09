package com.verifit.verifit.client.oauth2.config;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Oauth2Provider {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenUri;
    private final String userInfoUri;

    public Oauth2Provider(Oauth2Properties.Client client) {
        this(
                client.getClientId(),
                client.getClientSecret(),
                client.getRedirectUri(),
                client.getTokenUri(),
                client.getUserInfoUri()
        );
    }

    @Builder
    public Oauth2Provider(String clientId, String clientSecret, String redirectUri, String tokenUri, String userInfoUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }
}