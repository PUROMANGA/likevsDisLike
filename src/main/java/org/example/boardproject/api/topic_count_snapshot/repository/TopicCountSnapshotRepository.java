package org.example.boardproject.api.topic_count_snapshot.repository;

import org.example.boardproject.api.topic_count_snapshot.dto.TopicSnapshotRaningDto;
import org.example.boardproject.api.topic_count_snapshot.entity.TopicCountSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicCountSnapshotRepository extends JpaRepository<TopicCountSnapshot, Long> {

    @Query("select tcs.topicId, tcs.snapshotTime, tcs.topicLikeCount " +
            "from TopicCountSnapshot tcs " +
            "where tcs.snapshotTime = :time")
    List<TopicSnapshotRaningDto> findLikeCountsBySnapshotTime(LocalDateTime time);

    @Query("select tcs.topicId, tcs.snapshotTime, tcs.topicDislikeCount " +
            "from TopicCountSnapshot tcs " +
            "where tcs.snapshotTime = :time")
    List<TopicSnapshotRaningDto> findDislikeCountsBySnapshotTime(LocalDateTime time);

    @Query("select tcs.topicId, tcs.snapshotTime, tcs.topicAllCount " +
            "from TopicCountSnapshot tcs " +
            "where tcs.snapshotTime = :time")
    List<TopicSnapshotRaningDto> findAllCountsBySnapshotTime(LocalDateTime time);

}
