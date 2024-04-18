package org.laban.learning.spring.lesson4.withprotection.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;

import java.util.Set;

@Data
@Builder
public class UserDTO {
    @NotNull(groups = { ValidationGroup.Update.class })
    private Long id;

    @NotBlank(groups = { ValidationGroup.Create.class })
    private String username;

    @NotBlank(groups = { ValidationGroup.Create.class })
    @Email
    private String email;

    @NotBlank(groups = { ValidationGroup.Create.class })
    private String password;

    @NotEmpty(groups = { ValidationGroup.Create.class })
    private Set<@NotNull RoleType> roles;
}
