package org.laban.learning.spring.lessonfinal.model.kafka;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class UserRegisteredEvent {
    private Long userId;
    private Instant timestamp;
}
