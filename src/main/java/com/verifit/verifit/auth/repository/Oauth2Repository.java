package com.verifit.verifit.auth.repository;

import com.verifit.verifit.auth.domain.entity.Oauth2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2Repository extends JpaRepository<Oauth2, Long> {

    Optional<Oauth2> findByProviderAndProviderId(String provider, String providerId);
}
