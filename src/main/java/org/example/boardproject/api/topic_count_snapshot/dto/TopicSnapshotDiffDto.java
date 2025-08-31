package org.example.boardproject.api.topic_count_snapshot.dto;

import lombok.Getter;

@Getter
public class TopicSnapshotDiffDto {
    private final Long topicId;
    private final Long diff;

    public TopicSnapshotDiffDto(Long topicId, Long diff) {
        this.topicId = topicId;
        this.diff = diff;
    }
}
