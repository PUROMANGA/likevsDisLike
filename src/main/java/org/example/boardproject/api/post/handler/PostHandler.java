package org.example.boardproject.api.post.handler;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.post.entity.Post;
import org.example.boardproject.api.post.repository.PostRepository;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.springframework.stereotype.Component;

@Component("postGuard")
@RequiredArgsConstructor
public class PostHandler {
    private final PostRepository postRepository;
    public boolean matchedPostPassword(Long postId, String password) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("postId not found"));

        if(!post.getPassword().equals(password)) {
            throw new CustomRuntimeException(ErrorResponseStatus.LOGIN_ERROR);
        }

        return true;
    }
}
