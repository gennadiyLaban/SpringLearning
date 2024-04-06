package org.laban.learning.spring.lesson7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDTO {
    private final String id;
    private final String name;
    private final String email;
}
