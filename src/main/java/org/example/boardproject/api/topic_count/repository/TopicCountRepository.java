package org.example.boardproject.api.topic_count.repository;

import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicDto;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic_count.entity.TopicCount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface TopicCountRepository extends JpaRepository<TopicCount, Long> {

    boolean existsByTopicId(Long topicId);

    @Modifying
    @Query("update TopicCount t set t.topicAllCount = t.topicAllCount +1 where t.topicId =:topicId")
    void inTopicAllCountUp(Long topicId);

    @Modifying
    @Query("update TopicCount t set t.topicLikeCount = t.topicLikeCount +1 where t.topicId =:topicId")
    void inTopicLikeCountUp(Long topicId);

    @Modifying
    @Query("update TopicCount t set t.topicDisLikeCount = t.topicDisLikeCount +1 where t.topicId =:topicId")
    void inTopicDisLikeCountUp(Long topicId);

    @Query(value = "select topics.* " +
            "from topic_count t join topics on topics.id = t.topic_id " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.topic_like_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByLikeCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select topics.* " +
            "from topic_count t join topics on topics.id = t.topic_id " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.topic_dis_like_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByDislikeCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

    @Query(value = "select topics.* " +
            "from topic_count t join topics on topics.id = t.topic_id " +
            "where t.created_date between :oneHourAgo and :now " +
            "order by t.topic_all_count desc " +
            "limit 10",nativeQuery = true)
    List<Topic> findTop10ByCreatedDateBetweenOrderByEngagementCountDesc(LocalDateTime oneHourAgo, LocalDateTime now);

//    @Query(value = "select topics.* " +
//            "from topic_count t join topics on topics.id = t.topic_id " +
//            "where topics.genre = :genre " +
//            "order by t.topic_like_count desc " +
//            "limit 10", nativeQuery = true)
//    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByLikeCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);
//
//    @Query(value = "select topics.* " +
//            "from topic_count t join topics on topics.id = t.topic_id " +
//            "where topics.genre = :genre " +
//            "order by t.topic_dis_like_count desc " +
//            "limit 10", nativeQuery = true)
//    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByDislikeCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);
//
//    @Query(value = "select topics.* " +
//            "from topic_count t join topics on topics.id = t.topic_id " +
//            "where topics.genre = :genre " +
//            "order by t.topic_all_count desc " +
//            "limit 10", nativeQuery = true)
//    List<Topic> findTop10ByGenreAndCreatedDateBetweenOrderByEngagementCountDesc(String genre, LocalDateTime oneHourAgo, LocalDateTime now);

    Optional<TopicCount> findByTopicId(Long topicId);

    @Modifying
    @Transactional
    @Query(value = "insert into topic_count_snapshot" +
            "(snapshot_time, topic_id, topic_like_count, topic_dislike_count, topic_all_count)" +
            "select DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'),\n" +
            "       tc.topic_id," +
            "       tc.topic_like_count," +
            "       tc.topic_dis_like_count," +
            "       (tc.topic_like_count + tc.topic_dis_like_count)" +
            "from topic_count tc;", nativeQuery = true)
    void insertSnapshot();
}
