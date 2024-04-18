package org.laban.learning.spring.lesson4.withprotection.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_type"}))
@Entity(name = "role_records")
public class RoleRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;
}
