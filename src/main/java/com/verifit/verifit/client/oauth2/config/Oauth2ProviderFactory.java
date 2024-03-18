package com.verifit.verifit.client.oauth2.config;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;

import java.util.HashMap;
import java.util.Map;

public class Oauth2ProviderFactory {

    private final Map<String, Oauth2Provider> providers;

    public Oauth2ProviderFactory(Map<String, Oauth2Provider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public Oauth2Provider getByProviderName(String name) {
        if(!isValidOauth2Provider(name)){
            throw new ApiException(ExceptionCode.OAUTH2_PROVIDER_NOT_SUPPORTED);
        }
        return providers.get(name);
    }

    private boolean isValidOauth2Provider(String name){
        return providers.containsKey(name);
    }
}
