package org.example.boardproject.common.token.service;

import org.example.boardproject.common.token.common.LoginTokens;

public interface TokenService {
    LoginTokens login(String email);
}
