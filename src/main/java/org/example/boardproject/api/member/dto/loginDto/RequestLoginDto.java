package org.example.boardproject.api.member.dto.loginDto;

import lombok.Getter;

@Getter
public class RequestLoginDto {
    private final String email;
    private final String code;

    public RequestLoginDto(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
