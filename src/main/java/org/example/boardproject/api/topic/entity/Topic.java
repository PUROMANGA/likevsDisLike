package org.example.boardproject.api.topic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.topic.enums.Genre;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.common.base.BaseEntity;

@Entity
@Getter
@Table(name = "topics")
@NoArgsConstructor
public class Topic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    public Topic(RequestCreateTopic requestCreateTopic) {
        this.title = requestCreateTopic.getTopicName();
        this.genre = requestCreateTopic.getGenre();
    }

    public void update(RequestPatchTopic requestPatchTopic) {
        this.title = requestPatchTopic.getNewTopicName();
        this.genre = requestPatchTopic.getNewGenre();
    }
}
