package org.example.boardproject.api.topic_count_snapshot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class TopicCountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime snapshotTime;

    private Long topicId;

    private Long topicLikeCount;
    private Long topicDislikeCount;
    private Long topicAllCount;
}
