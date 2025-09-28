package org.example.boardproject.domain;

import org.example.boardproject.api.topic.common.ScheduledTopic;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.topic.service.TopicService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TopicTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ScheduledTopic scheduledTopic;

    @InjectMocks
    private TopicService topicService;


}
