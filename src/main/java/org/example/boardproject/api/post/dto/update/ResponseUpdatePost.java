package org.example.boardproject.api.post.dto.update;

import lombok.Getter;
import org.example.boardproject.api.post.entity.Post;

@Getter
public class ResponseUpdatePost {
    private final String username;
    private final String title;
    private final String content;

    public ResponseUpdatePost(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
