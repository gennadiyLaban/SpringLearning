package org.laban.learning.spring.lesson6.orderstatusservice.configuration;

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
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.kafkaGroupId}")
    private String kafkaMessageGroupId;

    @Bean
    public ProducerFactory<String, OrderStatusEvent> kafkaOrderStatusEventProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // из пакета org.apache.kafka.common.serialization
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // из пакета org.springframework.kafka.support.serializer

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, OrderStatusEvent> kafkaOrderStatusEventTemplate(ProducerFactory<String, OrderStatusEvent> kafkaMessageProducerFactory) {
        return new KafkaTemplate<>(kafkaMessageProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, OrderEvent> kafkaOrderEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // из пакета org.apache.kafka.common.serialization
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // из пакета org.springframework.kafka.support.serializer
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaMessageGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "org.laban.learning.spring.lesson6.common.kafkamessage");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(objectMapper));
    }

    @Bean("kafkaOrderEventConcurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> kafkaOrderEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderEvent> kafkaOOrderEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEvent>();
        factory.setConsumerFactory(kafkaOOrderEventConsumerFactory);

        return factory;
    }
}
