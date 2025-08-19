package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseGetTitleDto {
    private final Long id;
    private final String title;
    private final Long likeCount;
    private final Long dislikeCount;
    private final Long engagementCount;
    private final String genre;
    private final LocalDateTime createdDate;

    public ResponseGetTitleDto(Long id, String title, Long likeCount, Long dislikeCount, Long engagementCount, String genre, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.engagementCount = engagementCount;
        this.genre = genre;
        this.createdDate = createdDate;
    }
}
