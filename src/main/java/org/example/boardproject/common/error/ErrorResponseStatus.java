package org.example.boardproject.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorResponseStatus {

    LOGIN_ERROR(HttpStatus.FORBIDDEN, "아이디 또는 비밀 번호를 확인해주세요."),
    POST_PATCH_ERROR(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보가 존재하지 않습니다."),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 정보입니다.");

    private final HttpStatus httpStatus;
    private final String description;

    ErrorResponseStatus(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
