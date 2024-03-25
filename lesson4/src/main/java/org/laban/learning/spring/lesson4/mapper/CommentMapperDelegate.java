package org.laban.learning.spring.lesson4.mapper;

import org.laban.learning.spring.lesson4.model.Comment;
import org.laban.learning.spring.lesson4.service.PostService;
import org.laban.learning.spring.lesson4.service.UserService;
import org.laban.learning.spring.lesson4.web.dto.comment.CommentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class CommentMapperDelegate implements CommentMapper {
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
        return Comment.builder()
                .id(request.getId())
                .body(request.getBody())
                .user(request.getUserId() != null
                        ? userService.getUserById(request.getUserId())
                        : null
                )
                .post(request.getPostId() != null
                        ? postService.getPostById(request.getPostId())
                        : null)
                .build();
    }
}
