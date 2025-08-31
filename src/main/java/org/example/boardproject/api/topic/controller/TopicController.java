package org.example.boardproject.api.topic.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.topic.dto.create.dto.RequestCreateTopic;
import org.example.boardproject.api.topic.dto.create.dto.ResponseCreateTopic;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetGenreTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseGetTopicList;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicDto;
import org.example.boardproject.api.topic.dto.get.dto.ResponseTopicRankingDto;
import org.example.boardproject.api.topic.dto.patch.dto.RequestPatchTopic;
import org.example.boardproject.api.topic.dto.patch.dto.ResponsePatchTopic;
import org.example.boardproject.api.topic.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseCreateTopic> createTopic(@RequestBody @Valid RequestCreateTopic requestCreateTopic) {
        return ResponseEntity.ok(topicService.createTopicService(requestCreateTopic));
    }

    @PatchMapping("/admin/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsePatchTopic> patchTopic(@RequestBody @Valid RequestPatchTopic requestPatchTopic,
                                                         @PathVariable Long topicId) {
        return ResponseEntity.ok(topicService.patchTopicService(requestPatchTopic, topicId));
    }

    @DeleteMapping("/admin/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicService(topicId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<ResponseGetTopicList> getAllTopic(@CookieValue(value = "browserId", required = false) String browserId, HttpServletResponse response) {
        return ResponseEntity.ok(topicService.getAllTopicService(browserId, response));
    }

    @GetMapping("/genres")
    public ResponseEntity<ResponseGetGenreTopicList> getGenreTopic
            (@RequestParam String genre, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(topicService.getGenreTopicService(genre, pageable));
    }

    @GetMapping("/titles")
    public ResponseEntity<Page<ResponseTopicRankingDto>> getTitlesTopic
    (@RequestParam String title, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(topicService.getTitlesService(title, pageable));
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<ResponseTopicDto> getTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicService.getTopicService(topicId));
    }
}
