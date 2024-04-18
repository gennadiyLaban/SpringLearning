package org.laban.learning.spring.lesson7.withprotection.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.laban.learning.spring.lesson7.withprotection.model.TaskStatus;
import org.laban.learning.spring.lesson7.withprotection.web.validation.group.ValidationGroup;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
public class TaskDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotBlank(groups = ValidationGroup.Update.class)
    private final String id ;

    @NotBlank(groups = ValidationGroup.Create.class)
    private final String name;

    @NotBlank(groups = ValidationGroup.Create.class)
    private final String description;

    @NotNull(groups = ValidationGroup.Create.class)
    private final TaskStatus status;

    @With
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String authorId;

    private final String assigneeId;

    private final Set<@NotBlank String> observerIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Instant createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Instant updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO author;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO assignee;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<UserDTO> observers;
}
