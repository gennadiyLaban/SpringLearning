package org.laban.learning.spring.lesson4.withprotection.repository;

import org.laban.learning.spring.lesson4.withprotection.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query(value = "SELECT (user_id = :userId) from posts WHERE id = :postId", nativeQuery = true)
    Optional<Boolean> isPostOwner(Long userId, Long postId);
}
