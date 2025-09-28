package org.example.boardproject.api.topic.service;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardproject.api.image.entity.Image;
import org.example.boardproject.api.image.repository.ImageRepository;
import org.example.boardproject.api.topic.common.ScheduledTopic;
import org.example.boardproject.api.topic.common.TopicServiceHandler;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.*;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.enums.Genre;
import org.example.boardproject.api.topic.enums.RankingType;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.topic_count.entity.TopicCount;
import org.example.boardproject.api.topic_count.repository.TopicCountRepository;
import org.example.boardproject.api.topic_count_snapshot.common.ScheduledTopicCountSnapshot;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicCountRepository topicCountRepository;
    private final ScheduledTopic scheduledTopic;
    private final ImageRepository imageRepository;
    private final ScheduledTopicCountSnapshot scheduledTopicCountSnapshot;
    private final TopicServiceHandler topicServiceHandler;

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
            String newId = UuidCreator.getTimeOrderedEpoch().toString();
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
    public Page<ResponseRankingDto> getGenreTopicService(String genre, Pageable pageable) {
        Genre checkedGenre = Genre.checkGenre(genre);
        Page<Topic> topicList = topicRepository.findByGenre(checkedGenre, pageable);
        Map<Long, String> imageMap = imageRepository.findByTopicIdIn(topicList.stream().map(Topic::getId).toList()).stream().collect(Collectors.toMap(
                Image::getTopicId,Image::getImagePath));

        return topicList.map(t -> new ResponseRankingDto(t, imageMap.get(t.getId())));
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
     * 특정 토픽을 보여줌
     * @param topicId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseTopicDto getTopicService(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        Image image = imageRepository.findByTopicId(topicId);
        TopicCount topicCount = topicCountRepository.findByTopicId(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        return new ResponseTopicDto(topic, topicCount, image.getImagePath());
    }

    /**
     * 인기 토픽
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponseRankingDto> getTopicRanking(RankingType rankingType, Pageable pageable) {

        List<Long> list = switch (rankingType) {
            case LIKE -> scheduledTopicCountSnapshot.checkLikeRankingTopicId();
            case DISLIKE -> scheduledTopicCountSnapshot.checkDisLikeRankingTopicId();
            case ALL -> scheduledTopicCountSnapshot.checkAllRankingTopicId();
        };

        List<Topic> topicList = topicServiceHandler.checkTopicRanking(list);
        List<ResponseRankingDto> responseRankingDtoList = topicServiceHandler.buildRankingDtos(list, topicList);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responseRankingDtoList.size());

        List<ResponseRankingDto> pagedList = responseRankingDtoList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, responseRankingDtoList.size());
    }
}
