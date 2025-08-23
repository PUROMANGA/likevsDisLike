package org.example.boardproject.api.vote.repository;

import org.example.boardproject.api.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Vote findByBrowserIdAndTopicId(String browserId, Long topicId);
    boolean existsByBrowserIdAndTopicId(String browserId, Long topicId);
}
