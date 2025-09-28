package org.example.boardproject.api.vote.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.vote.dto.RequestVoteCreateDto;
import org.example.boardproject.api.vote.dto.ResponseVoteCreateDto;
import org.example.boardproject.api.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{topicId}")
    public ResponseEntity<ResponseVoteCreateDto> createVote(
            @PathVariable Long topicId, @RequestBody @Valid RequestVoteCreateDto requestVoteCreateDto, @CookieValue(value = "browserId", required = false) String browserId) {
        return ResponseEntity.ok(voteService.createVoteService(topicId, requestVoteCreateDto, browserId));
    }
}
