package org.laban.learning.spring.lesson4.withprotection.mapper;

import org.laban.learning.spring.lesson4.withprotection.model.Comment;
import org.laban.learning.spring.lesson4.withprotection.service.PostService;
import org.laban.learning.spring.lesson4.withprotection.service.UserService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

public abstract class CommentMapperDelegate implements CommentMapper {
    @Lazy
    @Autowired
    @Qualifier("delegate")
    private CommentMapper delegate;
    @Lazy
    @Autowired
    private PostService postService;
    @Lazy
    @Autowired
    private UserService userService;

    // resolved in delegate to avoid cycle dependencies between
    // PostMapper and CommentMapper
    @Override
    public Comment commentRequestDTOtoComment(CommentRequestDTO request) {
        return delegate.commentRequestDTOtoComment(request)
                .toBuilder()
                .post(request.getPostId() != null
                        ? postService.getPostById(request.getPostId())
                        : null)
                .build();
    }

    @Override
    public Comment commentRequestDTOtoComment(CommentRequestDTO request, Long userId) {
        return delegate.commentRequestDTOtoComment(request)
                .toBuilder()
                .user(userId != null
                        ? userService.getUserById(userId)
                        : null)
                .post(request.getPostId() != null
                        ? postService.getPostById(request.getPostId())
                        : null)
                .build();
    }
}
