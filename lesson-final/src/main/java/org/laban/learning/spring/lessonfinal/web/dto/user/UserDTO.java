package org.laban.learning.spring.lessonfinal.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.laban.learning.spring.lessonfinal.model.RoleType;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleType> roles;
}
