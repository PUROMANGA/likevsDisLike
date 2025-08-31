package org.example.boardproject.api.topic.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.common.ScheduledTopic;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetGenreTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicDto;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicRankingDto;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.enums.Genre;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.topic_count.entity.TopicCount;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicCountRepository topicCountRepository;
    private final ScheduledTopic scheduledTopic;

    /**
     * 토픽 생성
     * @param requestCreateTopic
     * @return
     */
    @Override
    @Transactional
    public ResponseCreateTopic createTopicService(RequestCreateTopic requestCreateTopic) {
        Topic topic = new Topic(requestCreateTopic);
        topicRepository.save(topic);
        return new ResponseCreateTopic(topic);
    }

    /**
     * 토픽 수정
     * @param requestPatchTopic
     * @param topicId
     * @return
     */
    @Override
    @Transactional
    public ResponsePatchTopic patchTopicService(RequestPatchTopic requestPatchTopic, Long topicId) {
        Topic topic = topicRepository.findByIdForUpdate(topicId).orElseThrow(() -> new RuntimeException("해당 토픽이 존재하지 않습니다."));
        topic.update(requestPatchTopic);
        topicRepository.save(topic);
        return new ResponsePatchTopic(topic);
    }

    /**
     * 토픽 삭제
     * @param topicId
     */
    @Override
    @Transactional
    public void deleteTopicService(Long topicId) {
        Topic topic = topicRepository.findByIdForUpdate(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        topicRepository.delete(topic);
    }

    /**
     * 토픽 랭킹(미리 캐시한 곳에서 가져오기)
     * 우리 사이트의 메인 페이지
     * @param browserId
     * @param response
     * @return
     */
    @Override
    @Cacheable(cacheNames = "topicRanking", key = "'TopicList'")
    @Transactional(readOnly = true)
    public ResponseGetTopicList getAllTopicService(String browserId, HttpServletResponse response) {

        if (browserId == null) {
            String newId = UUID.randomUUID().toString();
            ResponseCookie cookie = ResponseCookie.from("browserId", newId)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(365))
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }

        return scheduledTopic.createRealTimeTopicRanking();
    }

    /**
     * 특정 장르의 토픽을 클릭했을 때 랭킹으로 보여줌
     * @param genre
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseGetGenreTopicList getGenreTopicService(String genre, Pageable pageable) {
        Genre checkedGenre = Genre.checkGenre(genre);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);
        List<ResponseTopicRankingDto> likeTopicList = topicCountRepository.findTop10ByGenreAndCreatedDateBetweenOrderByLikeCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        List<ResponseTopicRankingDto> disLikeTopicList = topicCountRepository.findTop10ByGenreAndCreatedDateBetweenOrderByDislikeCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        List<ResponseTopicRankingDto> finalTopicList = topicCountRepository.findTop10ByGenreAndCreatedDateBetweenOrderByEngagementCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicRankingDto::new).toList();
        Page<ResponseTopicRankingDto> topicList = topicRepository.findByGenre(checkedGenre, pageable).map(ResponseTopicRankingDto::new);
        return new ResponseGetGenreTopicList(likeTopicList, disLikeTopicList, finalTopicList, topicList);
    }

    /**
     * 특정 title의 토픽을 검색했을 때 토픽을 보여줌.
     * @param title
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponseTopicRankingDto> getTitlesService(String title, Pageable pageable) {
        return topicRepository.findByTitle(title, pageable).map(ResponseTopicRankingDto::new);
    }

    /**
     * 특정 토픽을 보여줌(댓글은 보여주기 전)
     * @param topicId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseTopicDto getTopicService(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        TopicCount topicCount = topicCountRepository.findByTopicId(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        return new ResponseTopicDto(topic, topicCount);
    }
}
