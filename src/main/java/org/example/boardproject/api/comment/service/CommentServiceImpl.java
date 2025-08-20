package org.example.boardproject.api.comment.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.create.ResponseCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.dto.get.ResponseGetComment;
import org.example.boardproject.api.comment.entity.Comment;
import org.example.boardproject.api.comment.repository.CommentRepository;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.vote.common.VoteServiceHandler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;
    private final VoteServiceHandler voteServiceHandler;

    @Override
    @Transactional
    public ResponseCreateComment createCommentService(HttpServletRequest request, RequestCreateComment requestCreateComment, String browserId) {
        Topic topic = topicRepository.findById(requestCreateComment.getTopicId()).orElseThrow(() -> new RuntimeException("topic not found"));
        String ip = voteServiceHandler.createIp(request);
        Comment comment = new Comment(ip, topic, requestCreateComment, browserId);
        commentRepository.save(comment);
        return new ResponseCreateComment(comment);
    }

    @Override
    @Transactional
    public ResponseCreateComment createLikeCommentService(RequestCreateLikeComment requestCreateLikeComment) {
        Comment comment = commentRepository.findById(requestCreateLikeComment.getCommentId()).orElseThrow(() -> new RuntimeException("comment not found"));
        comment.update(requestCreateLikeComment);
        commentRepository.save(comment);
        return new  ResponseCreateComment(comment);
    }

    @Override
    @Transactional
    public void deleteCommentService(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<ResponseGetComment> getCommentService(Long topicId, Pageable pageable) {
        return commentRepository.findByTopicId(topicId, pageable).map(ResponseGetComment::new);
    }
}
