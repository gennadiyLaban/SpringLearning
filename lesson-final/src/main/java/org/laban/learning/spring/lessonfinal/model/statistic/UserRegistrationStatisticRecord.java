package org.laban.learning.spring.lessonfinal.model.statistic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Document(collection = "user_registrations")
public class UserRegistrationStatisticRecord {
    @Id
    private String id;
    private Long userId;
    private Instant timestamp;
}
