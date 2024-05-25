package com.verifit.verifit.global.jwt;

import com.verifit.verifit.auth.service.AuthService;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 3   ; // 3시간
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7   ; // 7일

    @Autowired
    @Lazy // 순환참조 방지. 추후 로직 분리로 해결하자.
    private MemberService memberService;

    public String generateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        return buildToken(claims, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(Member member){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        return buildToken(claims, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public TokenInfo extractMember(String accessToken){
        Claims claims = extractClaims(accessToken);
        Long userId = claims.get("id", Long.class);
        Member member = memberService.findMemberById(userId);

        return TokenInfo.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword()) // password는 필요한가?
//                .authorities(member.getAuthorities()) // authorities는 어디에 저장해야 하나?
                .build();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parse(token)
                .getBody();
    }

    private String buildToken(Map<String, Object> claims, long expireTime){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}
