package org.laban.learning.spring.lesson7.withprotection.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Builder
@Getter
@Setter
@Document(collection = "task")
public class Task {
    @Id
    private final String id ;
    private final String name;
    private final String description;
    @With
    private final Instant createdAt;
    @With
    private final Instant updatedAt;
    private final TaskStatus status;
    private final String authorId;
    private final String assigneeId;
    @With
    private final Set<String> observerIds;

    @Setter
    @ReadOnlyProperty
    private User author;

    @Setter
    @ReadOnlyProperty
    private User assignee;

    @Setter
    @ReadOnlyProperty
    private Set<User> observers;
}
