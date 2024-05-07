package org.laban.learning.spring.lessonfinal.configuration;

import org.laban.learning.spring.lessonfinal.model.kafka.HotelRoomBookedEvent;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfiguration {
    @Bean("userRegisteredEventContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent>
    kafkaUserRegisteredEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, UserRegisteredEvent> kafkaOrderStatusEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent>();
        factory.setConsumerFactory(kafkaOrderStatusEventConsumerFactory);

        return factory;
    }

    @Bean("hotelRoomBookedEventContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, HotelRoomBookedEvent>
    kafkaHotelRoomBookedEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, HotelRoomBookedEvent> kafkaOrderStatusEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, HotelRoomBookedEvent>();
        factory.setConsumerFactory(kafkaOrderStatusEventConsumerFactory);

        return factory;
    }
}
