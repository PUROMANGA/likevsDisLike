package org.example.boardproject.common.token.common;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS("Access Token"),
    REFRESH("Refresh Token");

    private final String description;

    TokenType(String description) {
        this.description = description;
    }
}
