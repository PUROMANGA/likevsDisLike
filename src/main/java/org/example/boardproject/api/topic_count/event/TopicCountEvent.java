package org.example.boardproject.api.topic_count.event;

import lombok.Getter;
import org.example.boardproject.api.vote.enums.VoteType;
import org.springframework.context.ApplicationEvent;

@Getter
public class TopicCountEvent extends ApplicationEvent {
    private final Long topicId;
    private final VoteType voteType;

    public TopicCountEvent(Object source, Long topicId, VoteType voteType) {
        super(source);
        this.topicId = topicId;
        this.voteType = voteType;
    }
}
