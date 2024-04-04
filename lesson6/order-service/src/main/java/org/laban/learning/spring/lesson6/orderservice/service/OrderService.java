package org.laban.learning.spring.lesson6.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderEvent;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderStatusEvent;
import org.laban.learning.spring.lesson6.orderservice.web.dto.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    @Value("${app.kafka.kafkaOrderTopic}")
    private String kafkaOrderTopic;

    private final KafkaTemplate<String, OrderEvent> orderEventTemplate;

    public void createOrder(Order order) {
        sendOrderEvent(order.toEvent());
    }

    private void sendOrderEvent(OrderEvent event) {
        orderEventTemplate.send(kafkaOrderTopic, event);
    }

    public void readStatus(OrderStatusEvent event) {
        log.info("Received message: {}", event);
    }
}
