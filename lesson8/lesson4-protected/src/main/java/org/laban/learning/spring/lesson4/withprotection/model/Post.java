package org.laban.learning.spring.lesson4.withprotection.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToMany
    private List<Category> categories;

    @With
    @ManyToOne
    @JoinColumn(updatable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
