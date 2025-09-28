package org.example.boardproject.api.image.repository;

import org.example.boardproject.api.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByTopicId(Long topicId);
    List<Image> findByTopicIdIn(Collection<Long> topicIds);
}
