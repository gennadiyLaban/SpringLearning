package org.laban.learning.spring.lesson4.withprotection.web.dto.post;

import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class PostListItemDTO {
    private Long id;
    private String title;
    private String description;
    private String body;
    private List<CategoryDTO> categories;
    private UserDTO user;
    private Integer commentsCount;
    private Instant createdAt;
    private Instant updatedAt;
}
