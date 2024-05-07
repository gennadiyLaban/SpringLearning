package org.laban.learning.spring.lessonfinal.service;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class StatisticService {
    @Value("${app.kafka.kafkaUserRegistrationTopic}")
    private String kafkaUserRegistrationTopic;

    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;

    public void sendUserRegistered(User user) {
        userRegisteredTemplate.send(kafkaUserRegistrationTopic, UserRegisteredEvent.builder()
                .userId(user.getId())
                .timestamp(Instant.now())
                .build());
    }
}
