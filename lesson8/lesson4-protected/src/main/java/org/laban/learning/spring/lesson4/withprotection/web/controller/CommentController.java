package org.laban.learning.spring.lesson4.withprotection.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.service.CommentService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> commentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentDTObyId(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<CommentListDTO> commentList(
            @RequestParam Long postId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(commentService.findAllForPostByDTO(
                postId, Pageable.ofSize(size).withPage(page)));
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody
            @Validated({ValidationGroup.Create.class})
            CommentRequestDTO request
    ) {
        var createdId = commentService.createCommentByDTO(request);
        return ResponseEntity.created(
                URI.create(MessageFormat.format("/api/v1/comment/{0}", createdId))
        ).build();
    }

    @PreAuthorize("@commentAuthManager.hasPermissionForUpdate(#userDetails, #request)")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody
            @Validated({ValidationGroup.Update.class})
            CommentRequestDTO request,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        commentService.updateCommentByDTO(request);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@commentAuthManager.hasPermissionsForDelete(#userDetails, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

}
