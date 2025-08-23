package org.example.boardproject.api.topic.dto.patch.dto;

import lombok.Getter;
import org.example.boardproject.api.topic.enums.Genre;

@Getter
public class RequestPatchTopic {
    private String newTopicName;
    private Genre newGenre;
}
