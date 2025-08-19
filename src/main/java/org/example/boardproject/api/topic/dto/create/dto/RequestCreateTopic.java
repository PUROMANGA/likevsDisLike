package org.example.boardproject.api.topic.dto.create.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.enums.Genre;

@Getter
public class RequestCreateTopic {
    private String topicName;
    private Genre genre;
}
