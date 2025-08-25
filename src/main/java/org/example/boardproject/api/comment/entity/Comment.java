package org.example.boardproject.api.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.enums.CommentType;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.vote.enums.VoteType;
import org.example.boardproject.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String browserId;
    private String ip;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    private String name;
    private String content;
    private Long likeCount;
    private Long disLikeCount;

    public Comment(String ip, Topic topic, RequestCreateComment requestCreateComment, String browserId, VoteType voteType) {
        this.topic = topic;
        this.browserId = browserId;
        this.ip = ip;
        this.voteType = voteType;

        if(requestCreateComment.getName() != null) {
            this.name = requestCreateComment.getName();
        } else {
            this.name = "ㅇㅇ";
        }
        this.content = requestCreateComment.getContent();
        this.likeCount = 0L;
        this.disLikeCount = 0L;
    }

    public void update(RequestCreateLikeComment requestCreateLikeComment) {
        if(requestCreateLikeComment.getCommentType().equals(CommentType.LIKE)) {
            this.increaseLikeCount();
        } else {
            this.increaseDisLikeCount();
        }
    }

    public void delete(String content) {
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }
    public void decreaseLikeCount() {
        this.likeCount--;
    }
    public void increaseDisLikeCount() {
        this.disLikeCount++;
    }
    public void decreaseDisLikeCount() {
        this.disLikeCount--;
    }
}
