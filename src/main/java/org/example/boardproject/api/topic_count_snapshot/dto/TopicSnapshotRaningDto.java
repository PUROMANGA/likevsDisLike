package org.example.boardproject.api.topic_count_snapshot.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopicSnapshotRaningDto {

    private final Long topicId;
    private final LocalDateTime snapshotTime;
    private final Long topicCount;

    public TopicSnapshotRaningDto(Long topicId, LocalDateTime snapshotTime, Long topicCount) {
        this.topicId = topicId;
        this.snapshotTime = snapshotTime;
        this.topicCount = topicCount;
    }
}
