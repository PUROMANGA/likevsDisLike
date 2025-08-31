package org.example.boardproject.api.topic.common;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicRankingDto;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTopic {

    private final TopicCountRepository topicCountRepository;

    /**
     * 매 시 정각마다 실행되어 최근 1시간 동안의 토픽 랭킹 데이터를 조회하고 캐시에 저장한다.
     * - 스케줄러(@Scheduled): 매 시 정각(0분)에 실행된다.
     * - 캐시(@CachePut): 항상 최신 데이터를 topicRanking 캐시에 "TopicList" 키로 저장한다.
     * - 트랜잭션(@Transactional(readOnly = true)): 읽기 전용 트랜잭션으로 실행한다.<
     * @return ResponseGetTopicList 최근 1시간 동안 생성된 토픽을
     *         좋아요 기준, 싫어요 기준, 최종 집계 기준으로 각각 조회하여 담은 DTO
     */
    @Scheduled(cron = "0 0 * * * *")
    @CachePut(cacheNames = "topicRanking", key = "'TopicList'")
    @Transactional(readOnly = true)
    public ResponseGetTopicList createRealTimeTopicRanking() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);
        List<ResponseTopicRankingDto> likeTopicList = topicCountRepository.findTop10ByCreatedDateBetweenOrderByLikeCountDesc(oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        List<ResponseTopicRankingDto> disLikeTopicList = topicCountRepository.findTop10ByCreatedDateBetweenOrderByDislikeCountDesc(oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        List<ResponseTopicRankingDto> finalTopicList = topicCountRepository.findTop10ByCreatedDateBetweenOrderByEngagementCountDesc(oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        return new ResponseGetTopicList(likeTopicList, disLikeTopicList, finalTopicList);
    }
}
