package org.example.boardproject.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.comment.dto.create.RequestCreateComment;
import org.example.boardproject.api.comment.dto.create.ResponseCreateComment;
import org.example.boardproject.api.comment.dto.createlike.RequestCreateLikeComment;
import org.example.boardproject.api.comment.dto.get.ResponseGetComment;
import org.example.boardproject.api.comment.entity.Comment;
import org.example.boardproject.api.comment.repository.CommentRepository;
import org.example.boardproject.api.topic.entity.Topic;
import org.example.boardproject.api.topic.repository.TopicRepository;
import org.example.boardproject.api.vote.entity.Vote;
import org.example.boardproject.api.vote.repository.VoteRepository;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;
    private final VoteRepository voteRepository;

    @Override
    @Transactional
    public ResponseCreateComment createCommentService(RequestCreateComment requestCreateComment, String browserId, Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        Vote vote = voteRepository.findByBrowserIdAndTopicId(browserId, topicId);
        Comment comment = new Comment(topic, requestCreateComment, browserId, vote.getVoteType());
        commentRepository.save(comment);
        return new ResponseCreateComment(comment);
    }

    @Override
    @Transactional
    public ResponseCreateComment createLikeCommentService(RequestCreateLikeComment requestCreateLikeComment, Long commentId) {
        Comment comment = commentRepository.findByIdForUpdate(commentId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        comment.update(requestCreateLikeComment);
        commentRepository.save(comment);
        return new  ResponseCreateComment(comment);
    }

    @Override
    @Transactional
    public void patchCommentService(Long commentId) {
        Comment comment = commentRepository.findByIdForUpdate(commentId).orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.NOT_FOUND));
        comment.delete("삭제된 댓글입니다.");
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<ResponseGetComment> getCommentService(Long topicId, Pageable pageable) {
        return commentRepository.findByTopicId(topicId, pageable).map(ResponseGetComment::new);
    }
}
