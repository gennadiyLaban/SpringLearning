package org.laban.learning.spring.lesson4.withprotection.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    private Set<RoleType> roles;
}
