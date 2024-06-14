package org.laban.learning.spring.lessonfinal.web.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.laban.learning.spring.lessonfinal.model.RoleType;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotEmpty;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
@Getter
public class UserDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotNull(groups = ValidationGroup.Update.class)
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String username;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String password;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    @Email
    private String email;

    @NotEmpty(groups = ValidationGroup.Create.class)
    @NullOrNotEmpty(groups = ValidationGroup.Update.class)
    private Set<@NotNull RoleType> roles;
}
