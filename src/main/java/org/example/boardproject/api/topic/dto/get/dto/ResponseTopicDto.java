package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.entity.Topic;

import java.time.LocalDateTime;

@Getter
public class ResponseTopicDto {
    private final Long id;
    private final String title;
    private final Long likeCount;
    private final Long dislikeCount;
    private final Long engagementCount;
    private final String genre;
    private final LocalDateTime createdDate;

    public ResponseTopicDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.likeCount = topic.getLikeCount();
        this.dislikeCount = topic.getDislikeCount();
        this.engagementCount = topic.getEngagementCount();
        this.genre = topic.getGenre().toString();
        this.createdDate = topic.getCreatedDate();
    }
}
