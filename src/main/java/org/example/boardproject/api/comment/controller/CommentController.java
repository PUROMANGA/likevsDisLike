package org.example.boardproject.api.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.create.ResponseCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.dto.get.ResponseGetComment;
import org.example.boardproject.api.comment.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{topicId}")
    public ResponseEntity<ResponseCreateComment> createComment(HttpServletRequest request,
                                                               @RequestBody @Valid RequestCreateComment requestCreateComment,
                                                               @CookieValue(value = "browserId", required = false) String browserId,
                                                               @PathVariable Long topicId) {
        return ResponseEntity.ok(commentService.createCommentService(request, requestCreateComment, browserId, topicId));
    }

    @PostMapping("/like/{commentId}")
    public ResponseEntity<ResponseCreateComment> createLikeComment(@RequestBody @Valid RequestCreateLikeComment requestCreateLikeComment,
                                                                   @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.createLikeCommentService(requestCreateLikeComment, commentId));
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> patchComment(@PathVariable Long commentId) {
        commentService.patchCommentService(commentId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<Slice<ResponseGetComment>> getComment(@PathVariable Long topicId,
            @PageableDefault(size = 10, sort = "createdDate", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentService(topicId, pageable));
    }
}
