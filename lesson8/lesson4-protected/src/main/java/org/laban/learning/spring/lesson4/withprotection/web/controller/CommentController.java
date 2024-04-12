package org.laban.learning.spring.lesson4.withprotection.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.service.CommentService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> commentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentDTObyId(id));
    }

    @GetMapping("/list")
    public ResponseEntity<CommentListDTO> commentList(
            @RequestParam Long postId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(commentService.findAllForPostByDTO(
                postId, Pageable.ofSize(size).withPage(page)));
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody
            @Validated({ValidationGroup.Create.class})
            CommentRequestDTO request,
            UriComponentsBuilder builder
    ) {
        var createdId = commentService.createCommentByDTO(request);
        return ResponseEntity.created(
                builder.path("/category").path("/{id}").buildAndExpand(createdId).toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody
            @Validated({ValidationGroup.Update.class})
            CommentRequestDTO request
    ) {
        commentService.updateCommentByDTO(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id
    ) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

}
