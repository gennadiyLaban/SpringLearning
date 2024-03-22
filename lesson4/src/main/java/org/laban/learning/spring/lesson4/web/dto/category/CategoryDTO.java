package org.laban.learning.spring.lesson4.web.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;

@Builder
@Data
public class CategoryDTO {
    @NotNull(groups = {ValidationGroup.Update.class})
    private Long id;
    @NotBlank
    private String name;
}
