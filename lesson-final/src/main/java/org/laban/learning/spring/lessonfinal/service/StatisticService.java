package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.mapper.BookingStatisticRecordMapper;
import org.laban.learning.spring.lessonfinal.mapper.UserRegistrationStatisticRecordMapper;
import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.model.kafka.HotelRoomBookedEvent;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.laban.learning.spring.lessonfinal.repository.statistic.BookingStatisticRecordRepository;
import org.laban.learning.spring.lessonfinal.repository.statistic.UserRegistrationStatisticRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class StatisticService {
    @Value("${app.kafka.kafkaUserRegistrationTopic}")
    private String kafkaUserRegistrationTopic;
    @Value("${app.kafka.kafkaHotelRoomBookingTopic}")
    private String kafkaHotelRoomBookingTopic;

    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;
    private final KafkaTemplate<String, HotelRoomBookedEvent> hotelRoomBookedTemplate;

    private final BookingStatisticRecordRepository bookingStatisticRepository;
    private final BookingStatisticRecordMapper bookingStatisticRecordMapper;

    private final UserRegistrationStatisticRecordRepository userRegistrationStatisticRecordRepository;
    private final UserRegistrationStatisticRecordMapper userRegistrationStatisticRecordMapper;

    public void sendUserRegistered(User user) {
        userRegisteredTemplate.send(kafkaUserRegistrationTopic, UserRegisteredEvent.builder()
                .userId(user.getId())
                .timestamp(Instant.now())
                .build());
    }

    @Transactional
    public void onUserRegistered(UserRegisteredEvent event) {
        userRegistrationStatisticRecordRepository.save(
                userRegistrationStatisticRecordMapper.eventToUserRegistrationStatisticRecord(event));
    }

    public void sendHotelRoomBooked(Booking booking) {
        hotelRoomBookedTemplate.send(kafkaHotelRoomBookingTopic, HotelRoomBookedEvent.builder()
                .bookingId(booking.getId())
                .userId(booking.getUser().getId())
                .hotelRoomId(booking.getRoom().getId())
                .startBooking(booking.getStartDate())
                .endBooking(booking.getEndDate())
                .timestamp(Instant.now())
                .build());
    }

    @Transactional
    public void onHotelRoomBooked(@Nonnull HotelRoomBookedEvent event) {
        bookingStatisticRepository.save(bookingStatisticRecordMapper.eventToBookingStatisticRecord(event));
    }
}
