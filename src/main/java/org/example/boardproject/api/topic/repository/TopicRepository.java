package org.example.boardproject.api.topic.repository;

import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.like_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByLikeCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.dislike_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByDislikeCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.engagement_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByEngagementCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now and t.genre = :genre " +
            "order by t.like_count desc " +
            "limit 10", nativeQuery = true)
    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByLikeCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now and t.genre = :genre " +
            "order by t.dislike_count desc " +
            "limit 10", nativeQuery = true)
    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByDislikeCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select * " +
            "from topics t " +
            "where t.created_date between :oneHourAgo and :now and t.genre = :genre " +
            "order by t.engagement_count desc " +
            "limit 10", nativeQuery = true)
    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByEngagementCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);

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
}
