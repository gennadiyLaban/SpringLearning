package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.service.PostService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostListRequest;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostRequestDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> postById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPostDTObyId(id));
    }

    @GetMapping("/list")
    public ResponseEntity<PostListDTO> postList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(
                postService.findAllByDTO(Pageable.ofSize(size).withPage(page))
        );
    }

    @PostMapping("/list")
    public ResponseEntity<PostListDTO> postList(@RequestBody @Valid PostListRequest request) {
        return ResponseEntity.ok(
                postService.findAllByDTO(request)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createPost(
            @RequestBody @Valid PostRequestDTO requestDTO,
            UriComponentsBuilder builder
    ) {
        var createdId = postService.createPostByDTO(requestDTO);
        return ResponseEntity.created(
                builder.path("/post").path("/{id}").buildAndExpand(createdId).toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody
            @Validated({ValidationGroup.Update.class})
            PostRequestDTO requestDTO
    ) {
        postService.updatePostByDTO(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

}
