package org.example.boardproject.api.comment.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.create.ResponseCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.dto.get.ResponseGetComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    ResponseCreateComment createCommentService(HttpServletRequest request, @Valid RequestCreateComment requestCreateComment, String browserId, Long topicId);
    ResponseCreateComment createLikeCommentService(RequestCreateLikeComment requestCreateLikeComment, Long commentId);
    void deleteCommentService(Long commentId);
    Slice<ResponseGetComment> getCommentService(Long topicId, Pageable pageable);
}
