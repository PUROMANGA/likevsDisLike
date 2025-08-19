package org.example.boardproject.api.topic.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.common.Genre;

@Getter
public class RequestCreateTopic {
    private String topicName;
    private Genre genre;
}
