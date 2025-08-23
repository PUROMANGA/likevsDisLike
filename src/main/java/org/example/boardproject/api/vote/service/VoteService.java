package org.example.boardproject.api.vote.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.boardproject.api.vote.dto.RequestVoteCreateDto;
import org.example.boardproject.api.vote.dto.ResponseVoteCreateDto;

public interface VoteService {
    ResponseVoteCreateDto createVoteService(Long topicId, RequestVoteCreateDto requestVoteCreateDto, HttpServletRequest request, String browserId);
}
