package org.laban.learning.spring.lessonfinal.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaStatisticEventListener {

    @KafkaListener(
            topics = "${app.kafka.kafkaUserRegistrationTopic}",
            groupId = "${app.kafka.kafkaGroupId}",
            containerFactory = "userRegisteredEventContainerFactory"
    )
    public void listenUserRegisteredEvent(
            @Payload UserRegisteredEvent event
    ) {
        log.info("event: {}", event);
    }
}
