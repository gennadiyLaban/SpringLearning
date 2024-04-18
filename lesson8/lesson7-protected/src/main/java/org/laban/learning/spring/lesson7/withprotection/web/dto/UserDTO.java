package org.laban.learning.spring.lesson7.withprotection.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.laban.learning.spring.lesson7.withprotection.model.RoleType;
import org.laban.learning.spring.lesson7.withprotection.web.validation.group.ValidationGroup;

import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
public class UserDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotBlank(groups = ValidationGroup.Update.class)
    private final String id;

    @NotBlank(groups = ValidationGroup.Create.class)
    private final String name;

    @NotBlank(groups = ValidationGroup.Create.class)
    private final String email;

    @NotBlank(groups = ValidationGroup.Create.class)
    private final String password;

    @NotEmpty(groups = ValidationGroup.Create.class)
    private final Set<@NotNull RoleType> roles;
}
