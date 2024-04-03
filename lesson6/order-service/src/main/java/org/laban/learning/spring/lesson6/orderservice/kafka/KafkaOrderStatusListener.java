package org.laban.learning.spring.lesson6.orderservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderStatusEvent;
import org.laban.learning.spring.lesson6.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaOrderStatusListener {
    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.kafkaOrderStatusTopic}",
            groupId = "${app.kafka.kafkaGroupId}",
            containerFactory = "kafkaOrderStatusEventConcurrentKafkaListenerContainerFactory"
    )
    public void listenOrderStatusEvent(
            @Payload OrderStatusEvent event,
            @Header(value = KafkaHeaders.RECEIVED_KEY) UUID key,
            @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(value = KafkaHeaders.PARTITION) Integer partition,
            @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        orderService.readStatus(event);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
