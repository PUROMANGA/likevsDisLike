package org.example.boardproject.common.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCustomRuntimeException(CustomRuntimeException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().getHttpStatus().value(),
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(e.getStatus().getHttpStatus()).body(errorResponse);
    }
}
