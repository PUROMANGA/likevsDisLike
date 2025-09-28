package org.example.boardproject.api.comment.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.example.boardproject.api.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class ResponseGetComment {
    private final Long id;
    private final Long topicId;
    private final String voteType;
    private final String name;
    private final String content;
    private final Long likeCount;
    private final Long disLikeCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdDate;

    public ResponseGetComment(Comment comment) {
        this.id = comment.getId();
        this.topicId = comment.getTopic().getId();
        this.voteType = comment.getVoteType().toString();
        this.name = comment.getName();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.disLikeCount = comment.getDisLikeCount();
        this.createdDate = comment.getCreatedDate();
    }
}
