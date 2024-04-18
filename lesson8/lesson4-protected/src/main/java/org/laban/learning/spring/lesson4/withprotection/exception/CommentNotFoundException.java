package org.laban.learning.spring.lesson4.withprotection.exception;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {
    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        this.commentId = commentId;
    }

}
