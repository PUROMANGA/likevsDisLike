package org.example.boardproject.api.post.dto.create;

import lombok.Getter;
import org.example.boardproject.api.post.entity.Post;

@Getter
public class ResponseCreatePost {
    private final String username;
    private final String title;
    private final String content;

    public ResponseCreatePost(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
