package org.laban.learning.spring.lesson7.withprotection.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.laban.learning.spring.lesson7.withprotection.web.validation.group.ValidationGroup;

@AllArgsConstructor
@Builder
@Getter
public class UserDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotBlank(groups = ValidationGroup.Update.class)
    private final String id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String email;
}
