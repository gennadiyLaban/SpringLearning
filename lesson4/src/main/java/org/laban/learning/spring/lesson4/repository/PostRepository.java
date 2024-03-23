package org.laban.learning.spring.lesson4.repository;

import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.security.CheckAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @CheckAuthorization(paramName = "post")
    default void deleteWithAuthorization(Post post) {
        delete(post);
    }
}
