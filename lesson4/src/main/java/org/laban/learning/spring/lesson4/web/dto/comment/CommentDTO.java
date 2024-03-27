package org.laban.learning.spring.lesson4.web.dto.comment;

import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.web.dto.user.UserDTO;

import java.time.Instant;

@Builder
@Data
public class CommentDTO {
    private Long id;
    private String body;
    private UserDTO user;
    private Long postId;
    private Instant createdAt;
    private Instant updatedAt;
}
