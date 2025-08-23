package org.example.boardproject.api.post.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.boardproject.api.post.dto.RequestPwDto;
import org.example.boardproject.api.post.dto.create.RequestCreatePost;
import org.example.boardproject.api.post.dto.create.ResponseCreatePost;
import org.example.boardproject.api.post.dto.get.ResponseGetPost;
import org.example.boardproject.api.post.dto.update.RequestUpdatePost;
import org.example.boardproject.api.post.dto.update.ResponseUpdatePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    ResponseCreatePost createPostService(RequestCreatePost requestCreatePost, HttpServletRequest request, String browserId);
    ResponseUpdatePost updatePostService(RequestUpdatePost requestUpdatePost, Long postId);
    Page<ResponseGetPost> getAllPostService(Pageable pageable);
    ResponseGetPost getPostService(Long postId, RequestPwDto requestPwDto);
    void deletePostService(Long postId, RequestPwDto requestPwDto);
}
