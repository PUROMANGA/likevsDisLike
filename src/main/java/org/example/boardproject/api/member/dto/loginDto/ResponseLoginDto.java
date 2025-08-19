package org.example.boardproject.api.member.dto.loginDto;

import lombok.Getter;
import org.example.boardproject.common.token.common.LoginTokens;

@Getter
public class ResponseLoginDto {
    private final String accessToken;
    private final String refreshToken;

    public ResponseLoginDto(LoginTokens loginTokens) {
        this.accessToken = loginTokens.getAccessToken();
        this.refreshToken = loginTokens.getRefreshToken();
    }
}
