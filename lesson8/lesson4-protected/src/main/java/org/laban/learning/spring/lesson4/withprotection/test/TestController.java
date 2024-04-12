package org.laban.learning.spring.lesson4.withprotection.test;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/findAllPosts")
    public ResponseEntity<PostListDTO> postscategories() {
        return ResponseEntity.ok(testService.fetchPosts());
    }

    @Data
    @Builder
    public static class PostListDTO {
        @Singular
        private List<PostDTO> posts;
    }

    @Data
    @Builder
    public static class PostDTO {
        private Long id;
        private String title;
        private String description;
        private String body;
        private Instant createdAt;
        private Instant updatedAt;
        @Singular
        private List<CategoryDTO> categories;
        private UserDTO user;
        private List<CommentDTO> comments;
    }

    @Data
    @Builder
    public static class CategoryDTO {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;
    }

    @Data
    @Builder
    public static class CommentDTO {
        private Long id;
        private String body;
        private String username;
        private Long postId;
        private Instant createdAt;
        private Instant updatedAt;
    }
}
