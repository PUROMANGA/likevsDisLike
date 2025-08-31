package org.example.boardproject.api.topic.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    @Query("select t " +
            "from Topic t " +
            "where t.genre =:genre " +
            "order by t.createdDate desc ")
    Page<Topic> findByGenre(Genre genre, Pageable pageable);

    @Query("select t " +
            "from Topic t " +
            "where t.title like %:title% " +
            "order by t.createdDate desc ")
    Page<Topic> findByTitle(String title, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "200"))
    @Query("select t from Topic t where t.id = :id")
    Optional<Topic> findByIdForUpdate(Long id);
}
