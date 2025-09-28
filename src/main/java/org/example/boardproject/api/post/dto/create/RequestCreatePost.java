package org.example.boardproject.api.post.dto.create;

import lombok.Getter;

@Getter
public class RequestCreatePost {
    private String username;
    private String password;
    private String title;
    private String content;
}
