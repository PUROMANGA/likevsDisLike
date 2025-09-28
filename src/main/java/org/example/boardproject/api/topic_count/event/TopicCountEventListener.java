package org.example.boardproject.api.topic_count.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardproject.api.topic_count.entity.TopicCount;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.api.vote.enums.VoteType;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicCountEventListener {
    private final TopicCountRepository topicCountRepository;

    @Async
    @EventListener
    @Transactional
    public void handleTopicCountEvent(TopicCountEvent topicCountEvent) {

        Long topicId = topicCountEvent.getTopicId();
        VoteType voteType = topicCountEvent.getVoteType();

        if (!topicCountRepository.existsByTopicId(topicId)) {
            topicCountRepository.save(new TopicCount(topicId, voteType));
        } else {
            topicCountRepository.inTopicAllCountUp(topicId);

            if (voteType == VoteType.LIKE) {
                topicCountRepository.inTopicLikeCountUp(topicId);
            } else {
                topicCountRepository.inTopicDisLikeCountUp(topicId);
            }
        }
    }
}
