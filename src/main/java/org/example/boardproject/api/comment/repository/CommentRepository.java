package org.example.boardproject.api.comment.repository;

import org.example.boardproject.api.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c from Comment c where c.topic.id =:topicId order by c.createdDate desc")
    Slice<Comment> findByTopicId(Long topicId, Pageable pageable);
}
