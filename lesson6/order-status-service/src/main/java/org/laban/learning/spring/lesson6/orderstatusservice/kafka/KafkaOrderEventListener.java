package org.laban.learning.spring.lesson6.orderstatusservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderEvent;
import org.laban.learning.spring.lesson6.orderstatusservice.service.OrderProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaOrderEventListener {
    private final OrderProcessingService orderProceedService;

    @KafkaListener(
            topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaGroupId}",
            containerFactory = "kafkaOrderEventConcurrentKafkaListenerContainerFactory"
    )
    public void listenOrderEvent(
            @Payload OrderEvent event,
            @Header(value = KafkaHeaders.RECEIVED_KEY) UUID key,
            @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(value = KafkaHeaders.PARTITION) Integer partition,
            @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        orderProceedService.proceedOrderEvent(event);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
