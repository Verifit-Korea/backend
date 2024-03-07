package com.verifit.verifit.client.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(Oauth2Properties.class)
public class Oauth2Config {

    private final Oauth2Properties properties;

    @Bean
    public Oauth2ProviderFactory oauth2ProviderFactory() {
        Map<String, Oauth2Provider> providers = Oauth2Adapter.getOauthProviders(properties);
        return new Oauth2ProviderFactory(providers);
    }

}
