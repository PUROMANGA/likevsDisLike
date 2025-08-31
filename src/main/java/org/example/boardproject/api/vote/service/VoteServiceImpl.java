package org.example.boardproject.api.vote.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic_count.event.TopicCountEvent;
import org.example.boardproject.api.vote.dto.RequestVoteCreateDto;
import org.example.boardproject.api.vote.dto.ResponseVoteCreateDto;
import org.example.boardproject.api.vote.entity.Vote;
import org.example.boardproject.api.vote.repository.VoteRepository;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public ResponseVoteCreateDto createVoteService(Long topicId, RequestVoteCreateDto requestVoteCreateDto, String browserId) {
        if(voteRepository.existsByTopicIdAndBrowserId(topicId, browserId)) {
            throw new CustomRuntimeException(ErrorResponseStatus.ALREADY_EXISTS);
        }
        Vote vote = new Vote(topicId, browserId, requestVoteCreateDto.getVoteType());
        voteRepository.save(vote);
        publisher.publishEvent(new TopicCountEvent(this, topicId, requestVoteCreateDto.getVoteType()));
        return new ResponseVoteCreateDto(vote);
    }
}
