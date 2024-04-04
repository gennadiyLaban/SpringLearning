package org.laban.learning.spring.lesson6.orderservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderEvent;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderStatusEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

@Configuration
public class KafkaConfiguration {

    @Bean("kafkaOrderStatusEventConcurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderStatusEvent> kafkaOrderStatusEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderStatusEvent> kafkaOrderStatusEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderStatusEvent>();
        factory.setConsumerFactory(kafkaOrderStatusEventConsumerFactory);

        return factory;
    }
}
