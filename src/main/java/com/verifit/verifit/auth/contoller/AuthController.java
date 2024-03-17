package com.verifit.verifit.auth.contoller;

import com.verifit.verifit.auth.domain.response.Oauth2Response;
import com.verifit.verifit.auth.dto.LoginRequestDTO;
import com.verifit.verifit.auth.dto.RegisterRequestDTO;
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

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO requestDTO) {
        return authService.loginUsingPassword(requestDTO.getEmail(), requestDTO.getPassword());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO requestDTO) {
        authService.registerUsingPassword(
            requestDTO.getEmail(),
            requestDTO.getPassword(),
            requestDTO.getNickname()
        );
    }
}
