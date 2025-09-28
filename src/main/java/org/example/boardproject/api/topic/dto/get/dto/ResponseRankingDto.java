package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.entity.Topic;

@Getter
public class ResponseRankingDto {
    private final Long topicId;
    private final String title;
    private final String imagePath;

    public ResponseRankingDto(Topic topic, String imagePath) {
        this.topicId = topic.getId();
        this.title = topic.getTitle();
        this.imagePath = imagePath;
    }
}
