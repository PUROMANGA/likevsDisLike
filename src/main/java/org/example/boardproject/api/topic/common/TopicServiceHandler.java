package org.example.boardproject.api.topic.common;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.image.entity.Image;
import org.example.boardproject.api.image.repository.ImageRepository;
import org.example.boardproject.api.topic.dto.get.dto.ResponseRankingDto;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TopicServiceHandler {

    private final TopicRepository topicRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public List<Topic> checkTopicRanking(List<Long> list) {

        List<Topic> topicList = topicRepository.findByIdIn(list);

        Map<Long, Integer> orderMap = new HashMap<>();

        for(int i= 0; i < list.size(); i++){
            orderMap.put(list.get(i), i);
        }

        return topicList.stream().sorted(Comparator.comparingInt(t -> orderMap.get(t.getId()))).toList();
    }

    public List<ResponseRankingDto> buildRankingDtos (List<Long> list, List<Topic> topicList) {
        Map<Long, String> imageMap = imageRepository.findByTopicIdIn(list).stream().collect(Collectors.toMap(
                Image::getTopicId,Image::getImagePath));
        return topicList.stream().map(t -> new ResponseRankingDto(t, imageMap.get(t.getId()))).toList();
    }
}
