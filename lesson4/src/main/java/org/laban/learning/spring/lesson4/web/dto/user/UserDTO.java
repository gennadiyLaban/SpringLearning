package org.laban.learning.spring.lesson4.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;

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
}
