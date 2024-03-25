package org.laban.learning.spring.lesson4.repository;

import org.laban.learning.spring.lesson4.model.Comment;
import org.laban.learning.spring.lesson4.security.CheckAuthorization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    @CheckAuthorization(paramName = "comment")
    default void deleteWithAuthorization(Comment comment) {
        delete(comment);
    }
}
