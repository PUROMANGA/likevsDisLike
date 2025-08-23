package org.example.boardproject.api.post.dto.get;

import lombok.Getter;
import org.example.boardproject.api.post.entity.Post;

@Getter
public class ResponseGetPost {
    private String username;
    private String title;
    private String content;

    public ResponseGetPost(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
