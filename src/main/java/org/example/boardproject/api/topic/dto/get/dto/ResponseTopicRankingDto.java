package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic_count.entity.TopicCount;

import java.time.LocalDateTime;

@Getter
public class ResponseTopicRankingDto {
    private final Long id;
    private final String title;
    private final String genre;
    private final LocalDateTime createdDate;

    public ResponseTopicRankingDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.genre = topic.getGenre().toString();
        this.createdDate = topic.getCreatedDate();
    }
}
