package org.example.boardproject.api.image.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.common.base.BaseEntity;

@Getter
@Table(name= "images")
@Entity
@NoArgsConstructor

public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    private String fileName;

    private Long topicId;

    public Image(String imagePath, Long topicId, String fileName) {
        this.imagePath = imagePath;
        this.topicId = topicId;
        this.fileName = fileName;
    }
}
