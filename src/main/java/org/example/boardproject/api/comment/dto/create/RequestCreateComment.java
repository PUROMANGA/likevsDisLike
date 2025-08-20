package org.example.boardproject.api.comment.dto.create;

import lombok.Getter;
import org.example.boardproject.api.vote.enums.VoteType;

@Getter
public class RequestCreateComment {
    private Long topicId;
    private VoteType voteType;
    private String name;
    private String content;
}
