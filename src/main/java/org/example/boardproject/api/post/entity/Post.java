package org.example.boardproject.api.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.post.dto.create.RequestCreatePost;
import org.example.boardproject.api.post.dto.update.RequestUpdatePost;
import org.example.boardproject.common.base.BaseEntity;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String browserId;
    private String ip;
    private String username;
    private String password;
    private String title;
    private String content;

    public Post(RequestCreatePost requestCreatePost, String ip, String browserId) {
        this.browserId = browserId;
        this.ip = ip;

        if(!requestCreatePost.getUsername().isBlank()) {
            this.username = requestCreatePost.getUsername();
        } else {
            this.username = "dd";
        }

        this.password = requestCreatePost.getPassword();
        this.title = requestCreatePost.getTitle();
        this.content = requestCreatePost.getContent();
    }

    public void update(RequestUpdatePost requestUpdatePost) {

        if(!requestUpdatePost.getUsername().isBlank()) {
            this.username = requestUpdatePost.getUsername();
        } else {
            this.username = "dd";
        }

        this.title = requestUpdatePost.getTitle();
        this.content = requestUpdatePost.getContent();
    }
}
