package org.example.boardproject.api.topic.dto.get.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ResponseGetGenreTopicList {
    private final List<ResponseTopicRankingDto> likeTopicList;
    private final List<ResponseTopicRankingDto> disLikeTopicList;
    private final List<ResponseTopicRankingDto> finalTopicList;
    private final Page<ResponseTopicRankingDto> topicList;

    public ResponseGetGenreTopicList(List<ResponseTopicRankingDto> likeTopicList, List<ResponseTopicRankingDto> disLikeTopicList, List<ResponseTopicRankingDto> finalTopicList, Page<ResponseTopicRankingDto> topicList) {
        this.likeTopicList = likeTopicList;
        this.disLikeTopicList = disLikeTopicList;
        this.finalTopicList = finalTopicList;
        this.topicList = topicList;
    }
}
