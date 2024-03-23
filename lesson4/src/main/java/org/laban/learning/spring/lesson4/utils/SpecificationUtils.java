package org.laban.learning.spring.lesson4.utils;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lesson4.model.Category;
import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.model.User;
import org.laban.learning.spring.lesson4.web.dto.post.PostListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public class SpecificationUtils {
    private SpecificationUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static Specification<Post> specificationOf(PostListRequest request) {
        return Specification.where(authorsSpecificationOf(request))
                .and(categorySpecificationOf(request));
    }

    private static Specification<Post> authorsSpecificationOf(PostListRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(request.getAuthors())) {
                return null;
            }

            return root
                    .get(Post.Fields.user).get(User.Fields.id)
                    .in(request.getAuthors());
        };
    }

    private static Specification<Post> categorySpecificationOf(@Nonnull PostListRequest request) {
        return (root, query, cb) -> {
            var categoryIds = request.getCategories();
            if (CollectionUtils.isEmpty(categoryIds)) {
                return null;
            }
            return root
                    .<Post, Category>join(Post.Fields.categories)
                    .get(Category.Fields.id)
                    .in(categoryIds);
        };
    }
}
