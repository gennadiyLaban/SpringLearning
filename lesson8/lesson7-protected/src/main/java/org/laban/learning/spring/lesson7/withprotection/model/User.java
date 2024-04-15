package org.laban.learning.spring.lesson7.withprotection.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private final String id;
    @Indexed
    private final String name;
    private final String email;
    private final String password;
    private final Set<RoleType> roles;
}
