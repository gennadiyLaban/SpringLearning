package org.laban.learning.spring.lessonfinal.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
@Getter
public class UserListDTO {
    private List<UserDTO> users;
}
