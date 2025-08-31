package org.example.boardproject.api.topic_count.event;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic_count.entity.TopicCount;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.api.vote.enums.VoteType;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TopicCountEventListener {
    private final TopicCountRepository topicCountRepository;

    @Async
    @EventListener
    @Transactional
    public void handleTopicCountEvent(TopicCountEvent topicCountEvent) {
        Long topicId = topicCountEvent.getTopicId();

        if(!topicCountRepository.existsByTopicId(topicCountEvent.getTopicId())) {
            topicCountRepository.save(new TopicCount(topicId, topicCountEvent.getVoteType()));
        }

        topicCountRepository.inTopicAllCountUp(topicId);

        if(topicCountEvent.getVoteType() == VoteType.LIKE) {
            topicCountRepository.inTopicLikeCountUp(topicId);
        } else {
            topicCountRepository.inTopicDisLikeCountUp(topicId);
        }
    }
}
