package org.example.boardproject.api.topic_count.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.vote.enums.VoteType;
import org.example.boardproject.common.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "topicCount")
public class TopicCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long topicId;
    private Long topicLikeCount;
    private Long topicDisLikeCount;
    private Long topicAllCount;
    private LocalDateTime snapshotTime;

    public TopicCount(Long topicId, VoteType voteType) {
        this.topicId = topicId;
        this.topicAllCount = 1L;

        if(voteType.equals(VoteType.LIKE)) {
            this.topicLikeCount = 1L;
            this.topicDisLikeCount = 0L;
        } else {
            this.topicLikeCount = 0L;
            this.topicDisLikeCount = 1L;
        }
    }

    public void update(VoteType voteType) {
        this.increaseCount();
        if(voteType.equals(VoteType.LIKE)) {
            this.increaseLikeCount();
        } else {
            this.increaseDisLikeCount();
        }
    }

    public void increaseLikeCount() {
        this.topicLikeCount++;
    }

    public void increaseDisLikeCount() {
        this.topicDisLikeCount++;
    }

    public void increaseCount() {
        this.topicAllCount++;
    }
}
