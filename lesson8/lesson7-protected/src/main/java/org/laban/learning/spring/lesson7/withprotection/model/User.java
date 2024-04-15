package org.laban.learning.spring.lesson7.withprotection.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private final String id;
    private final String name;
    private final String email;
}
