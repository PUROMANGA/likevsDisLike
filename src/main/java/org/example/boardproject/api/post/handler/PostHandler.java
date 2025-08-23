package org.example.boardproject.api.post.handler;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.post.entity.Post;
import org.example.boardproject.api.post.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component("postGuard")
@RequiredArgsConstructor
public class PostHandler {
    private final PostRepository postRepository;
    public boolean matchedPostPassword(Long postId, String password) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("postId not found"));
        return post.getPassword().equals(password);
    }
}
