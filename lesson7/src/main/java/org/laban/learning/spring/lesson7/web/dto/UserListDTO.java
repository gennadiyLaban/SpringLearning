package org.laban.learning.spring.lesson7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class UserListDTO {
    private final List<UserDTO> users;
}
