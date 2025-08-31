package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseGetTopicList {
    private final List<ResponseRankingDto> likeTopicList;
    private final List<ResponseRankingDto> disLikeTopicList;
    private final List<ResponseRankingDto> finalTopicList;

    public ResponseGetTopicList(List<ResponseRankingDto> likeTopicList, List<ResponseRankingDto> disLikeTopicList, List<ResponseRankingDto> finalTopicList) {
        this.likeTopicList = likeTopicList;
        this.disLikeTopicList = disLikeTopicList;
        this.finalTopicList = finalTopicList;
    }
}
