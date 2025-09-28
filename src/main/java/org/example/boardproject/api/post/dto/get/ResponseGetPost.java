package org.example.boardproject.api.post.dto.get;

import lombok.Getter;
import org.example.boardproject.api.post.entity.Post;

@Getter
public class ResponseGetPost {
    private Long id;
    private String username;
    private String title;
    private String content;

    public ResponseGetPost(Post post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
