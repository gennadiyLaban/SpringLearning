package org.laban.learning.spring.lessonfinal.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.laban.learning.spring.lessonfinal.model.RoleType;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
@Getter
public class UserDTO {
    @Null(groups = ValidationGroup.Create.class)
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String username;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String password;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String email;

    @NotEmpty(groups = ValidationGroup.Create.class)
    private Set<@NotNull RoleType> roles;
}
