package org.example.boardproject.api.topic.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetGenreTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicDto;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    ResponseCreateTopic createTopicService(RequestCreateTopic requestCreateTopic);
    ResponsePatchTopic patchTopicService(RequestPatchTopic requestPatchTopic);
    void deleteTopicService(Long topicId);
    ResponseGetTopicList getAllTopicService(String browserId, HttpServletResponse response);
    ResponseGetGenreTopicList getGenreTopicService(String genre, Pageable pageable);
    Page<ResponseTopicDto> getTitlesService(String title, Pageable pageable);
    ResponseTopicDto getTopicService(Long topicId);
}
