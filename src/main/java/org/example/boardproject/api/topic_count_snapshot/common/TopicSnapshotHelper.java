package org.example.boardproject.api.topic_count_snapshot.common;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.api.topic_count_snapshot.dto.TopicSnapshotDiffDto;
import org.example.boardproject.api.topic_count_snapshot.dto.TopicSnapshotRaningDto;
import org.example.boardproject.api.topic_count_snapshot.enums.TopicCountSnapshotType;
import org.example.boardproject.api.topic_count_snapshot.repository.TopicCountSnapshotRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor

public class TopicSnapshotHelper {

    private final TopicCountSnapshotRepository topicCountSnapshotRepository;

    @Transactional
    public List<Long> resolveTrendingTopicIds(LocalDateTime now, LocalDateTime oneHourAgo, TopicCountSnapshotType topicCountSnapshotType, int num) {
        List<TopicSnapshotRaningDto> topicNowRanking = loadTopicSnapshotCounts(now, topicCountSnapshotType);
        List<TopicSnapshotRaningDto> topicOneHourRanking = loadTopicSnapshotCounts(oneHourAgo, topicCountSnapshotType);
        Map<Long, Long> map = mapSnapshotCountsByTopicId(topicOneHourRanking);
        return checkTopicRanking(topicNowRanking, map, num);
    }

    public List<TopicSnapshotRaningDto> loadTopicSnapshotCounts(LocalDateTime snapshotTime, TopicCountSnapshotType topicCountSnapshotType) {
        List<TopicSnapshotRaningDto> topicRanking;

        switch (topicCountSnapshotType) {
            case LIKE -> {
                topicRanking = topicCountSnapshotRepository.findLikeCountsBySnapshotTime(snapshotTime);
            }
            case DISLIKE -> {
                topicRanking = topicCountSnapshotRepository.findDislikeCountsBySnapshotTime(snapshotTime);
            }
            case ALL -> {
                topicRanking = topicCountSnapshotRepository.findAllCountsBySnapshotTime(snapshotTime);
            }
            default -> throw new IllegalArgumentException("Unknown type: " + topicCountSnapshotType);
        }
        return topicRanking;
    }

    public Map<Long, Long> mapSnapshotCountsByTopicId(List<TopicSnapshotRaningDto> topicOneHourAgoRanking) {
        return topicOneHourAgoRanking.stream()
                .collect(Collectors.toMap(
                        TopicSnapshotRaningDto::getTopicId,
                        TopicSnapshotRaningDto::getTopicCount)
                );
    }

    public List<Long> checkTopicRanking(List<TopicSnapshotRaningDto> topicNowRanking, Map<Long, Long> beforeMap, int num) {
        return topicNowRanking.stream()
                .map(t -> {
                    Long beforeCount = beforeMap.getOrDefault(t.getTopicId(), 0L);
                    Long diff = t.getTopicCount() - beforeCount;
                    return new TopicSnapshotDiffDto(t.getTopicId(), diff);
                })
                .sorted(Comparator.comparingLong(TopicSnapshotDiffDto::getDiff).reversed())
                .limit(num)
                .map(TopicSnapshotDiffDto::getTopicId)
                .toList();
    }
}
