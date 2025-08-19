package org.example.boardproject.common.token.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String email;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean blackList;

    public Token(String token, String email, TokenType tokenType) {
        this.token = token;
        this.email = email;
        this.tokenType = tokenType;
        this.blackList = false;
    }
}
