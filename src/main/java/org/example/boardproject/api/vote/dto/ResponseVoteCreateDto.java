package org.example.boardproject.api.vote.dto;

import lombok.Getter;
import org.example.boardproject.api.vote.entity.Vote;
import org.example.boardproject.api.vote.enums.VoteType;

@Getter
public class ResponseVoteCreateDto {
    private final Long id;
    private final Long topicId;
    private final String browserId;
    private final String ip;
    private final String voteType;

    public ResponseVoteCreateDto(Vote vote) {
        this.id = vote.getId();
        this.topicId = vote.getTopic().getId();
        this.browserId = vote.getBrowserId();
        this.ip = vote.getIp();
        this.voteType = vote.getVoteType().toString();
    }
}
