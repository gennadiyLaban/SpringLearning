package org.laban.learning.spring.lesson4.withprotection.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;

@Data
@Builder
public class CommentRequestDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotNull(groups = ValidationGroup.Update.class)
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String body;

    @NotNull(groups = ValidationGroup.Create.class)
    private Long postId;
}
