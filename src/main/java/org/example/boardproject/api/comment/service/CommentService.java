package org.example.boardproject.api.comment.service;

import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.create.ResponseCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.dto.get.ResponseGetComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    ResponseCreateComment createCommentService(RequestCreateComment requestCreateComment, String browserId, Long topicId);
    ResponseCreateComment createLikeCommentService(RequestCreateLikeComment requestCreateLikeComment, Long commentId);
    void patchCommentService(Long commentId);
    Slice<ResponseGetComment> getCommentService(Long topicId, Pageable pageable);
}
