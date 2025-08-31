package org.example.boardproject.api.vote.dto;

import lombok.Getter;
import org.example.boardproject.api.vote.entity.Vote;

@Getter
public class ResponseVoteCreateDto {
    private final Long id;
    private final Long topicId;
    private final String browserId;
    private final String voteType;

    public ResponseVoteCreateDto(Vote vote) {
        this.id = vote.getId();
        this.topicId = vote.getTopicId();
        this.browserId = vote.getBrowserId();
        this.voteType = vote.getVoteType().toString();
    }
}
