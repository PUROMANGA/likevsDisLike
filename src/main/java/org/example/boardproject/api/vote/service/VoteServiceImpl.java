package org.example.boardproject.api.vote.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.vote.common.VoteServiceHandler;
import org.example.boardproject.api.vote.dto.RequestVoteCreateDto;
import org.example.boardproject.api.vote.dto.ResponseVoteCreateDto;
import org.example.boardproject.api.vote.entity.Vote;
import org.example.boardproject.api.vote.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final TopicRepository topicRepository;
    private final VoteRepository voteRepository;
    private final VoteServiceHandler voteServiceHandler;

    @Override
    @Transactional
    public ResponseVoteCreateDto createVoteService(RequestVoteCreateDto requestVoteCreateDto, HttpServletRequest request, String browserId) {
        Topic topic = topicRepository.findById(requestVoteCreateDto.getTopicId()).orElseThrow(() -> new RuntimeException("topic not found"));
        String ip = voteServiceHandler.createIp(request);
        Vote vote = new Vote(topic, browserId, ip, requestVoteCreateDto.getVoteType());
        voteRepository.save(vote);
        topic.updateCount(vote);
        topicRepository.save(topic);
        return new ResponseVoteCreateDto(vote);
    }
}
