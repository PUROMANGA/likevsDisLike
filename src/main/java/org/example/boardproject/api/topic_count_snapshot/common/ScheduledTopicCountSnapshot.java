package org.example.boardproject.api.topic_count_snapshot.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.api.topic_count_snapshot.enums.TopicCountSnapshotType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTopicCountSnapshot {
    private final TopicCountRepository topicCountRepository;
    private final TopicSnapshotHelper topicSnapshotHelper;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void insetSnapShotForTopicCount() {
        topicCountRepository.insertSnapshot();
    }

//    @Scheduled(cron = "0 0 * * * *")
    @Transactional
//    @CachePut(cacheNames = "topicRanking", key = "'LikeTopicList'")
    public List<Long> checkLikeRankingTopicId() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime oneHourAgo = now.minusHours(1);
        return topicSnapshotHelper.resolveTrendingTopicIds(now, oneHourAgo, TopicCountSnapshotType.LIKE, 12);
    }

//    @Scheduled(cron = "0 50 * * * *")
    @Transactional
//    @CachePut(cacheNames = "topicRanking", key = "'DisLikeTopicList'")
    public List<Long> checkDisLikeRankingTopicId() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime oneHourAgo = now.minusHours(1);
        return topicSnapshotHelper.resolveTrendingTopicIds(now, oneHourAgo, TopicCountSnapshotType.DISLIKE, 12);
    }

//    @Scheduled(cron = "0 50 * * * *")
    @Transactional
//    @CachePut(cacheNames = "topicRanking", key = "'AllTopicList'")
    public List<Long> checkAllRankingTopicId() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime oneHourAgo = now.minusHours(1);
        return topicSnapshotHelper.resolveTrendingTopicIds(now, oneHourAgo, TopicCountSnapshotType.ALL, 12);
    }
}
