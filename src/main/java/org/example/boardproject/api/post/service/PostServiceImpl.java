package org.example.boardproject.api.post.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.post.dto.RequestPwDto;
import org.example.boardproject.api.post.dto.create.RequestCreatePost;
import org.example.boardproject.api.post.dto.create.ResponseCreatePost;
import org.example.boardproject.api.post.dto.get.ResponseGetPost;
import org.example.boardproject.api.post.dto.update.RequestUpdatePost;
import org.example.boardproject.api.post.dto.update.ResponseUpdatePost;
import org.example.boardproject.api.post.entity.Post;
import org.example.boardproject.api.post.repository.PostRepository;
import org.example.boardproject.api.vote.common.VoteServiceHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final VoteServiceHandler voteServiceHandler;

    /**
     * 게시글 생성
     * @param requestCreatePost
     * @param request
     * @param browserId
     * @return
     */
    @Override
    @Transactional
    public ResponseCreatePost createPostService(RequestCreatePost requestCreatePost, HttpServletRequest request, String browserId) {
        String ip = voteServiceHandler.createIp(request);
        Post post = new Post(requestCreatePost, ip, browserId);
        postRepository.save(post);
        return new ResponseCreatePost(post);
    }

    /**
     * 게시글 수정
     * @param requestUpdatePost
     * @param postId
     * @return
     */
    @Override
    @Transactional
    public ResponseUpdatePost updatePostService(RequestUpdatePost requestUpdatePost, Long postId) {
        Post post = postRepository.findByIdForUpdate(postId).orElseThrow(() -> new RuntimeException("postId not found"));

        if(!post.getPassword().equals(requestUpdatePost.getPassword())) {
            throw new RuntimeException("password not match");
        }

        post.update(requestUpdatePost);
        postRepository.save(post);
        return new ResponseUpdatePost(post);
    }

    /**
     * 게시글 전체 조회
     * @param pageable
     * @return
     */

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseGetPost> getAllPostService(Pageable pageable) {
        return postRepository.findAll(pageable).map(ResponseGetPost::new);
    }

    /**
     * 게시글 단일 조회
     * @param postId
     * @param requestPwDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseGetPost getPostService(Long postId, RequestPwDto requestPwDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("postId not found"));
        if(!post.getPassword().equals(requestPwDto.getPw())) {
            throw new RuntimeException("password not match");
        }
        return new ResponseGetPost(post);
    }

    /**
     * 게시글 삭제
     * @param postId
     * @param requestPwDto
     */
    @Override
    @Transactional
    public void deletePostService(Long postId, RequestPwDto requestPwDto) {
        Post post = postRepository.findByIdForUpdate(postId).orElseThrow(() -> new RuntimeException("postId not found"));
        if(!post.getPassword().equals(requestPwDto.getPw())) {
            throw new RuntimeException("password not match");
        }
        postRepository.delete(post);
    }
}
