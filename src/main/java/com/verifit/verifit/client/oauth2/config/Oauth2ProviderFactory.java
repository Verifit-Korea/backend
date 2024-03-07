package com.verifit.verifit.client.oauth2.config;

import java.util.HashMap;
import java.util.Map;

public class Oauth2ProviderFactory {

    private final Map<String, Oauth2Provider> providers;

    public Oauth2ProviderFactory(Map<String, Oauth2Provider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public Oauth2Provider getByProviderName(String name) {
        if(!isValidOauth2Provider(name)){
            throw new RuntimeException();
        }
        return providers.get(name);
    }

    private boolean isValidOauth2Provider(String name){
        return providers.containsKey(name);
    }
}
