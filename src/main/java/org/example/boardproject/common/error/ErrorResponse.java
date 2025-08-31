package org.example.boardproject.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String path;
}
