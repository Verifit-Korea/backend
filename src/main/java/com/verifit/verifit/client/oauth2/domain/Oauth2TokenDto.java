package com.verifit.verifit.client.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Oauth2TokenDto(
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("access_token")
        String accessToken,
        String scope
) {
}
