package org.example.boardproject.api.vote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.vote.enums.VoteType;
import org.example.boardproject.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "votes")
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;
    private String browserId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public Vote(Long topicId, String browserId, VoteType voteType) {
        this.topicId = topicId;
        this.browserId = browserId;
        this.voteType = voteType;
    }
}
