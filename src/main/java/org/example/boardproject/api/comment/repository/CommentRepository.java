package org.example.boardproject.api.comment.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.boardproject.api.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "200"))
    @Query("select c from Comment c where c.id = :id")
    Optional<Comment> findByIdForUpdate(Long id);

    @Query("select c from Comment c where c.topic.id =:topicId order by c.createdDate desc")
    Slice<Comment> findByTopicId(Long topicId, Pageable pageable);
}
