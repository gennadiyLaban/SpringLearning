package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Entity(name = "user_roles")
public class UserRole {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;
}
