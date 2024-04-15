package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.service.PostService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostListRequest;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostRequestDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.MessageFormat;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> postById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPostDTObyId(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<PostListDTO> postList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(
                postService.findAllByDTO(Pageable.ofSize(size).withPage(page))
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping("/list")
    public ResponseEntity<PostListDTO> postList(@RequestBody @Valid PostListRequest request) {
        return ResponseEntity.ok(
                postService.findAllByDTO(request)
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createPost(
            @RequestBody @Valid PostRequestDTO requestDTO,
            UriComponentsBuilder builder
    ) {
        var createdId = postService.createPostByDTO(requestDTO);
        return ResponseEntity.created(
                URI.create(MessageFormat.format("/api/v1/post/{0}", createdId))
        ).build();
    }

    @PreAuthorize("@postAuthManager.hasPermissionForUpdate(#userDetails, #requestDTO)")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody
            @Validated({ValidationGroup.Update.class})
            PostRequestDTO requestDTO,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        postService.updatePostByDTO(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@postAuthManager.hasPermissionsForDelete(#userDetails, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
