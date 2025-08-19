package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseGetTopicList {
    private final List<ResponseTopicDto> likeTopicList;
    private final List<ResponseTopicDto> disLikeTopicList;
    private final List<ResponseTopicDto> finalTopicList;

    public ResponseGetTopicList(List<ResponseTopicDto> likeTopicList, List<ResponseTopicDto> disLikeTopicList, List<ResponseTopicDto> finalTopicList) {
        this.likeTopicList = likeTopicList;
        this.disLikeTopicList = disLikeTopicList;
        this.finalTopicList = finalTopicList;
    }
}
