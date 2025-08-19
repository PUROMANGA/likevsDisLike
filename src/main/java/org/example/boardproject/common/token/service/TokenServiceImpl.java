package org.example.boardproject.common.token.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.member.common.MemberRole;
import org.example.boardproject.common.token.common.JwtUtil;
import org.example.boardproject.common.token.common.LoginTokens;
import org.example.boardproject.common.token.common.Token;
import org.example.boardproject.common.token.common.TokenType;
import org.example.boardproject.common.token.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public LoginTokens login(String email) {
        String accessToken = jwtUtil.createAccessToken(email, MemberRole.PARTICIPANT.getDescription());
        String refreshToken = jwtUtil.createRefreshToken(email);
        tokenRepository.save(new Token(accessToken, email, TokenType.ACCESS));
        tokenRepository.save(new Token(refreshToken, email, TokenType.REFRESH));
        return new LoginTokens(accessToken, refreshToken);
    }
}
