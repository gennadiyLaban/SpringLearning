package org.laban.learning.spring.lesson4.withprotection.repository;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lesson4.withprotection.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    @Query(value = "SELECT (user_id = :userId) from comments WHERE id = :commentId", nativeQuery = true)
    Optional<Boolean> isCommentOwner(@Nonnull Long userId, @Nonnull Long commentId);
}
