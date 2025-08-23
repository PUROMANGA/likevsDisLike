package org.example.boardproject.api.topic.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.common.ScheduledTopic;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetGenreTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicDto;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.enums.Genre;
import org.example.boardproject.api.topic.repository.TopicRepository;
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
    private final ScheduledTopic scheduledTopic;

    @Override
    @Transactional
    public ResponseCreateTopic createTopicService(RequestCreateTopic requestCreateTopic) {
        Topic topic = new Topic(requestCreateTopic);
        topicRepository.save(topic);
        return new ResponseCreateTopic(topic);
    }

    @Override
    @Transactional
    public ResponsePatchTopic patchTopicService(RequestPatchTopic requestPatchTopic, Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("해당 토픽이 존재하지 않습니다."));
        topic.update(requestPatchTopic);
        topicRepository.save(topic);
        return new ResponsePatchTopic(topic);
    }

    @Override
    @Transactional
    public void deleteTopicService(Long topicId) {
        topicRepository.deleteById(topicId);
    }

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

    @Override
    @Transactional(readOnly = true)
    public ResponseGetGenreTopicList getGenreTopicService(String genre, Pageable pageable) {
        Genre checkedGenre = Genre.checkGenre(genre);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);
        List<ResponseTopicDto> likeTopicList = topicRepository.findTop10ByGenreAndCreatedDateBetweenOrderByLikeCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicDto::new).toList();
        List<ResponseTopicDto> disLikeTopicList = topicRepository.findTop10ByGenreAndCreatedDateBetweenOrderByDislikeCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicDto::new).toList();
        List<ResponseTopicDto> finalTopicList = topicRepository.findTop10ByGenreAndCreatedDateBetweenOrderByEngagementCountDesc(genre, oneHourAgo, now).stream().map(ResponseTopicDto::new).toList();
        Page<ResponseTopicDto> topicList = topicRepository.findByGenre(checkedGenre, pageable).map(ResponseTopicDto::new);
        return new ResponseGetGenreTopicList(likeTopicList, disLikeTopicList, finalTopicList, topicList);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseTopicDto> getTitlesService(String title, Pageable pageable) {
        return topicRepository.findByTitle(title, pageable).map(ResponseTopicDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseTopicDto getTopicService(Long topicId) {
        return topicRepository.findById(topicId).map(ResponseTopicDto::new).orElseThrow(() -> new RuntimeException("해당 토픽이 존재하지 않습니다."));
    }
}
