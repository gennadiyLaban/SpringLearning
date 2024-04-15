package org.laban.learning.spring.lesson4.withprotection.web.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;

import java.util.List;

@Builder
@Data
public class PostRequestDTO {
    @Null(groups = { ValidationGroup.Create.class })
    @NotNull(groups = { ValidationGroup.Update.class })
    private Long id;

    @NotBlank(groups = { ValidationGroup.Create.class })
    private String title;

    private String description;

    @NotBlank(groups = { ValidationGroup.Create.class })
    private String body;

    @NotEmpty(groups = { ValidationGroup.Create.class })
    private List<@NotNull Long> categories;
}
