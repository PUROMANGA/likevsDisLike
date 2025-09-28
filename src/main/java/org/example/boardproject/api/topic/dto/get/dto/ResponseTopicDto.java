package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic_count.entity.TopicCount;

import java.time.LocalDateTime;

@Getter
public class ResponseTopicDto {
    private final Long id;
    private final String title;
    private final String genre;
    private final Long topicLikeCount;
    private final Long topicDisLikeCount;
    private final Long topicAllCount;
    private final LocalDateTime createdDate;
    private final String imagePath;

    public ResponseTopicDto(Topic topic, TopicCount topicCount, String imagePath) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.genre = topic.getGenre().toString();
        this.topicLikeCount = topicCount.getTopicLikeCount();
        this.topicDisLikeCount = topicCount.getTopicDisLikeCount();
        this.topicAllCount = topicCount.getTopicAllCount();
        this.createdDate = topic.getCreatedDate();
        this.imagePath = imagePath;
    }
}
