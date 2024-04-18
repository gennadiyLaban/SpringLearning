package org.laban.learning.spring.lesson4.withprotection.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.exception.CommentNotFoundException;
import org.laban.learning.spring.lesson4.withprotection.exception.PostNotFoundException;
import org.laban.learning.spring.lesson4.withprotection.mapper.CommentMapper;
import org.laban.learning.spring.lesson4.withprotection.model.Comment;
import org.laban.learning.spring.lesson4.withprotection.repository.CommentRepository;
import org.laban.learning.spring.lesson4.withprotection.repository.PostRepository;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.utils.BeanUtils;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public CommentDTO getCommentDTObyId(@Nonnull Long id) {
        return commentMapper.commentToCommentDTO(getCommentById(id));
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(@Nonnull Long id) {
        return findCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Comment> findCommentById(@Nonnull Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public CommentListDTO findAllForPostByDTO(Long postId, Pageable pageable) {
        return commentMapper.commentPageToCommentListDTO(postId, findAllForPost(postId, pageable));
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAllForPost(Long postId, Pageable pageable) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        return commentRepository.findAllByPostId(postId, pageable);
    }

    @Transactional
    public Long createCommentByDTO(CommentRequestDTO request) {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var upsertComment = commentMapper.commentRequestDTOtoComment(request, userDetails.getId());

        return commentRepository.save(upsertComment).getId();
    }

    @Transactional
    public void updateCommentByDTO(CommentRequestDTO request) {
        var upsertComment = commentMapper.commentRequestDTOtoComment(request);
        var existedComment = getCommentById(upsertComment.getId());

        BeanUtils.copyNonNullProperties(upsertComment, existedComment);
    }

    @Transactional
    public void deleteCommentById(@Nonnull Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isCommentOwner(@Nonnull Long userId, @Nonnull Long commentId) {
        return commentRepository.isCommentOwner(userId, commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
}
