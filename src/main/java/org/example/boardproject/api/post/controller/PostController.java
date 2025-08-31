package org.example.boardproject.api.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.post.dto.RequestPwDto;
import org.example.boardproject.api.post.dto.create.RequestCreatePost;
import org.example.boardproject.api.post.dto.create.ResponseCreatePost;
import org.example.boardproject.api.post.dto.get.ResponseGetPost;
import org.example.boardproject.api.post.dto.update.RequestUpdatePost;
import org.example.boardproject.api.post.dto.update.ResponseUpdatePost;
import org.example.boardproject.api.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResponseCreatePost> createPost(
            @RequestBody @Valid RequestCreatePost requestCreatePost, @CookieValue(value = "browserId", required = false) String browserId) {
        return ResponseEntity.ok(postService.createPostService(requestCreatePost, browserId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ResponseUpdatePost> updatePost(@RequestBody @Valid RequestUpdatePost requestUpdatePost,
                                                         @PathVariable Long postId) {
        return ResponseEntity.ok(postService.updatePostService(requestUpdatePost, postId));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseGetPost>> getAllPost(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPostService(pageable));
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postGuard.matchedPostPassword(#postId, #requestPwDto.pw)")
    public ResponseEntity<ResponseGetPost> getPost(@PathVariable Long postId, @RequestBody @Valid RequestPwDto requestPwDto) {
        return ResponseEntity.ok(postService.getPostService(postId, requestPwDto));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postGuard.matchedPostPassword(#postId, #requestPwDto.pw)")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestBody @Valid RequestPwDto requestPwDto) {
        postService.deletePostService(postId, requestPwDto);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
