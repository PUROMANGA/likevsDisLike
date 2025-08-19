package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ResponseGetGenreTopicList {
    private final List<ResponseTopicDto> likeTopicList;
    private final List<ResponseTopicDto> disLikeTopicList;
    private final List<ResponseTopicDto> finalTopicList;
    private final Page<ResponseTopicDto> topicList;

    public ResponseGetGenreTopicList(List<ResponseTopicDto> likeTopicList, List<ResponseTopicDto> disLikeTopicList, List<ResponseTopicDto> finalTopicList, Page<ResponseTopicDto> topicList) {
        this.likeTopicList = likeTopicList;
        this.disLikeTopicList = disLikeTopicList;
        this.finalTopicList = finalTopicList;
        this.topicList = topicList;
    }
}
