package org.example.boardproject.api.comment.dto.create;

import lombok.Getter;
import org.example.boardproject.api.comment.entity.Comment;

@Getter
public class ResponseCreateComment {
    private final Long id;
    private final Long topicId;
    private final String name;
    private final String content;
    private final String voteType;
    private final Long likeCount;
    private final Long disLikeCount;

    public ResponseCreateComment(Comment comment) {
        this.id = comment.getId();
        this.topicId = comment.getTopic().getId();
        this.name = comment.getName();
        this.content = comment.getContent();
        this.voteType = comment.getVoteType().toString();
        this.likeCount = comment.getLikeCount();
        this.disLikeCount = comment.getDisLikeCount();
    }
}
