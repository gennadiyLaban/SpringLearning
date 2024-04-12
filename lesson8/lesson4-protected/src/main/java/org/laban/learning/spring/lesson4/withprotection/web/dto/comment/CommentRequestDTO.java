package org.laban.learning.spring.lesson4.withprotection.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;

@Data
@Builder
public class CommentRequestDTO {
    @NotNull(groups = ValidationGroup.Update.class)
    private Long id;
    @NotBlank
    private String body;
    private Long userId;
    @NotNull(groups = ValidationGroup.Create.class)
    private Long postId;
}
