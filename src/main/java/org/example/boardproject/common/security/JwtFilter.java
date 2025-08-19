package org.example.boardproject.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardproject.common.token.common.JwtUtil;
import org.example.boardproject.common.token.common.Token;
import org.example.boardproject.common.token.repository.TokenRepository;
import org.example.boardproject.common.token.service.CustomUserDetailsService;
import org.example.boardproject.common.token.userdetails.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        List<String> whiteList = List.of(
                "/members/signup",
                "/members/login");

        if (whiteList.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.resolveToken(request);

        if(token != null) {
            Token foundToken = tokenRepository.findByEmail(jwtUtil.extractClaims(token).getSubject()).orElseThrow(() -> new RuntimeException("토큰이 존재하지 않습니다."));
            if(foundToken.getBlackList()) {
                throw new RuntimeException("로그아웃 된 토큰입니다.");
            }
        }

        if(StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            try {
                Claims claims = jwtUtil.extractClaims(token);
                String email = claims.getSubject();
                String role = claims.get("userRole", String.class);

                CustomUserDetails customUserDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(customUserDetails, null,
                                Collections.singletonList(new SimpleGrantedAuthority(role)));

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (Exception e) {
                log.warn("JWT 처리 중 오류 발생: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 처리 실패");
            }
        }
        filterChain.doFilter(request, response);
    }
}
