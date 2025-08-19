package org.example.boardproject.common.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    private final UserDetailsService userDetailsService;
    private static final String BEARER_PREFIX  = "Bearer ";

    @Value("${jwt.refreshToken.time}")
    private long accessTokenExpiration;
    @Value("${jwt.accessToken.time}")
    private long refreshTokenExpiration;
    @Value("${jwt.secretKey}")
    private String secretKey;

    private static Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email, String userRole) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userRole", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(accessTokenExpiration))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(refreshTokenExpiration))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // 비밀키로 파싱할 준비
                    .build()
                    .parseClaimsJws(token); // 여기서 서명, 만료 등 다 검증됨
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어있습니다.");
        }
        return false;
    }
}
