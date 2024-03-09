package com.verifit.verifit.client.oauth2.config;

import java.util.HashMap;
import java.util.Map;

public class Oauth2Adapter {

    private Oauth2Adapter() {}

    public static Map<String, Oauth2Provider> getOauthProviders(Oauth2Properties properties) {
        Map<String, Oauth2Provider> oauth2Provider = new HashMap<>();

        properties.getClient()
                .forEach((key, value) -> oauth2Provider.put(key, new Oauth2Provider(value)));
        return oauth2Provider;
    }
}
