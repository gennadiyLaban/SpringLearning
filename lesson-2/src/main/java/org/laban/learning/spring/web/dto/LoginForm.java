package org.laban.learning.spring.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data @NoArgsConstructor
public class LoginForm {
    private String username;
    private String password;
}
