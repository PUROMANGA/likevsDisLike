package org.example.boardproject.api.post.dto.update;

import lombok.Getter;

@Getter
public class RequestUpdatePost {
    private String username;
    private String password;
    private String title;
    private String content;
}
