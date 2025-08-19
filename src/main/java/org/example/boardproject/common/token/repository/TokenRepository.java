package org.example.boardproject.common.token.repository;

import org.example.boardproject.common.token.common.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
   Optional<Token> findByEmail(String ignoredEmail);
}
