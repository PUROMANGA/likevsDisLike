package org.example.boardproject.api.topic.common;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseRankingDto;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic_count_snapshot.common.TopicSnapshotHelper;
import org.example.boardproject.api.topic_count_snapshot.enums.TopicCountSnapshotType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTopic {

    private final TopicServiceHandler topicServiceHandler;
    private final TopicSnapshotHelper topicSnapshotHelper;


    //    @Scheduled(cron = "0 0 * * * *")
//    @CachePut(cacheNames = "topicRanking", key = "'TopicList'")
    @Transactional
    public ResponseGetTopicList createRealTimeTopicRanking() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime oneHourAgo = now.minusHours(1);

        List<ResponseRankingDto> likeRanking = getRankingResult(now, oneHourAgo, TopicCountSnapshotType.LIKE);
        List<ResponseRankingDto> DislikeRanking = getRankingResult(now, oneHourAgo, TopicCountSnapshotType.DISLIKE);
        List<ResponseRankingDto> AllRanking = getRankingResult(now, oneHourAgo, TopicCountSnapshotType.ALL);

        return new ResponseGetTopicList(likeRanking, DislikeRanking, AllRanking);
    }

    public List<ResponseRankingDto> getRankingResult(LocalDateTime now, LocalDateTime oneHourAgo, TopicCountSnapshotType topicCountSnapshotType) {
        List<Long> RankingIdList = topicSnapshotHelper.resolveTrendingTopicIds(now, oneHourAgo, topicCountSnapshotType, 3);
        List<Topic> topicList = topicServiceHandler.checkTopicRanking(RankingIdList);
        return topicServiceHandler.buildRankingDtos(RankingIdList, topicList);
    }
}
