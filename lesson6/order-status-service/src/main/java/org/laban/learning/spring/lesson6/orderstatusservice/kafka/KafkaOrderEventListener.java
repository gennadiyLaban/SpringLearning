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
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
            @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic,
            @Header(value = KafkaHeaders.PARTITION, required = false) Integer partition,
            @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp
    ) {
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
        orderProceedService.proceedOrderEvent(event);
    }
}
