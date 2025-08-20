package org.example.boardproject.api.comment.dto.get;

import lombok.Getter;
import org.example.boardproject.api.comment.entity.Comment;

@Getter
public class ResponseGetComment {
    private final Long id;
    private final Long topicId;
    private final String voteType;
    private final String name;
    private final String content;
    private final Long likeCount;
    private final Long disLikeCount;

    public ResponseGetComment(Comment comment) {
        this.id = comment.getId();
        this.topicId = comment.getTopic().getId();
        this.voteType = comment.getVoteType().toString();
        this.name = comment.getName();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.disLikeCount = comment.getDisLikeCount();
    }
}
