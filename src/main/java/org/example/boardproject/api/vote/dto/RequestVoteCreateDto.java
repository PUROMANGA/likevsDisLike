package org.example.boardproject.api.vote.dto;

import lombok.Getter;
import org.example.boardproject.api.vote.enums.VoteType;

@Getter
public class RequestVoteCreateDto {
    private VoteType voteType;
}
