package org.laban.learning.spring.lesson6.orderstatusservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderEvent;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderStatusEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProcessingService {
    @Value("${app.kafka.kafkaOrderStatusTopic}")
    private String orderStatusTopic;

    private final KafkaTemplate<String, OrderStatusEvent> kafkaOrderStatusTemplate;

    public void proceedOrderEvent(OrderEvent event) {
        log.info("Received message: {}", event);
        var instant = Instant.now();
        kafkaOrderStatusTemplate.send(orderStatusTopic, OrderStatusEvent.builder()
                .status(instant.toEpochMilli() / 2 == 0 ? "CREATED" : "PROCESSING")
                .date(instant)
                .build());
    }
}
