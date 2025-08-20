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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String browserId;
    private String ip;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public Vote(Topic topic, String browserId, String ip, VoteType voteType) {
        this.topic = topic;
        this.browserId = browserId;
        this.ip = ip;
        this.voteType = voteType;
    }
}
