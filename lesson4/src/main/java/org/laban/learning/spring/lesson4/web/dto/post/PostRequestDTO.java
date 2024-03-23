package org.laban.learning.spring.lesson4.web.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;

import java.util.List;

@Builder
@Data
public class PostRequestDTO {
    @NotNull(groups = {ValidationGroup.Update.class})
    private Long id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String body;
    private List<Long> categories;
    @NotNull
    private Long userId;
}
