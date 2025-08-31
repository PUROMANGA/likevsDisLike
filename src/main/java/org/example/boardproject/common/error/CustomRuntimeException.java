package org.example.boardproject.common.error;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException{
    private final ErrorResponseStatus status;

    public CustomRuntimeException(ErrorResponseStatus status) {
        super(status.getDescription());
        this.status = status;
    }
}
