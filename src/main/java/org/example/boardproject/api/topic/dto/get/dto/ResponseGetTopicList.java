package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseGetTopicList {
    private final List<ResponseTopicRankingDto> likeTopicList;
    private final List<ResponseTopicRankingDto> disLikeTopicList;
    private final List<ResponseTopicRankingDto> finalTopicList;

    public ResponseGetTopicList(List<ResponseTopicRankingDto> likeTopicList, List<ResponseTopicRankingDto> disLikeTopicList, List<ResponseTopicRankingDto> finalTopicList) {
        this.likeTopicList = likeTopicList;
        this.disLikeTopicList = disLikeTopicList;
        this.finalTopicList = finalTopicList;
    }
}
