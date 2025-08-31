package org.example.boardproject.api.post.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.boardproject.api.comment.entity.Comment;
import org.example.boardproject.api.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdForUpdate(Long id);
}
