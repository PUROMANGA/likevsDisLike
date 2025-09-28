package org.example.boardproject.api.topic.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.*;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.example.boardproject.api.topic.enums.RankingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    ResponseCreateTopic createTopicService(RequestCreateTopic requestCreateTopic);
    ResponsePatchTopic patchTopicService(RequestPatchTopic requestPatchTopic, Long topicId);
    void deleteTopicService(Long topicId);
    ResponseGetTopicList getAllTopicService(String browserId, HttpServletResponse response);
    Page<ResponseRankingDto> getGenreTopicService(String genre, Pageable pageable);
    Page<ResponseTopicRankingDto> getTitlesService(String title, Pageable pageable);
    ResponseTopicDto getTopicService(Long topicId);
    Page<ResponseRankingDto> getTopicRanking(RankingType rankingType, Pageable pageable);
}
