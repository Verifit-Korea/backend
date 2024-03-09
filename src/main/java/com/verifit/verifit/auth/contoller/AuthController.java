package com.verifit.verifit.auth.contoller;

import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{provider}")
    public Oauth2Response authenticate(
            @PathVariable String provider,
            @RequestParam String code
    ){
        return authService.authenticate(provider, code);
    }
}