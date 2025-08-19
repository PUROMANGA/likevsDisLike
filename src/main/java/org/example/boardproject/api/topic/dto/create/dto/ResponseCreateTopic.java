package org.example.boardproject.api.topic.dto.create.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.enums.Genre;
import org.example.boardproject.api.topic.entity.Topic;

import java.time.LocalDateTime;

@Getter
public class ResponseCreateTopic {
    private final Long id;
    private final String title;
    private final Genre genre;
    private final LocalDateTime createdDate;

    public ResponseCreateTopic(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.genre = topic.getGenre();
        this.createdDate = topic.getCreatedDate();
    }
}
