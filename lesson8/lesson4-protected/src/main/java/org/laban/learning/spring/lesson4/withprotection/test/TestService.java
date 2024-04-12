package org.laban.learning.spring.lesson4.withprotection.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson4.withprotection.model.Category;
import org.laban.learning.spring.lesson4.withprotection.model.Comment;
import org.laban.learning.spring.lesson4.withprotection.model.Post;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.laban.learning.spring.lesson4.withprotection.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private final PostRepository postRepository;

    @Transactional
    public TestController.PostListDTO fetchPosts() {
        var posts = postRepository.findAll();
        var postDTOs = posts.stream().map(this::postToDto).toList();
        return TestController.PostListDTO.builder()
                .posts(postDTOs)
                .build();
    }

    private TestController.PostDTO postToDto(Post post) {
        return TestController.PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .body(post.getBody())
                .categories(categoriesToDtoList(post.getCategories()))
                .user(userToDto(post.getUser()))
                .comments(commentsToDtoList(post.getComments()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private TestController.UserDTO userToDto(User user) {
        return TestController.UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    private List<TestController.CategoryDTO> categoriesToDtoList(List<Category> categories) {
        return categories.stream()
                .map(category -> TestController.CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();
    }

    private List<TestController.CommentDTO> commentsToDtoList(List<Comment> comments) {
        return comments.stream()
                .map(comment -> TestController.CommentDTO.builder()
                        .id(comment.getId())
                        .body(comment.getBody())
                        .username(comment.getUser().getUsername())
                        .postId(comment.getPost().getId())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .toList();
    }


}
