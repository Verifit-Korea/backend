package com.verifit.verifit.global.filter;


import com.verifit.verifit.global.jwt.JwtProvider;
import com.verifit.verifit.global.jwt.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!isValidAuthHeader(authHeader)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        TokenInfo tokenInfo = jwtProvider.extractMember(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenInfo, null, tokenInfo.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isValidAuthHeader(String authHeader){
        return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ");
    }
}
