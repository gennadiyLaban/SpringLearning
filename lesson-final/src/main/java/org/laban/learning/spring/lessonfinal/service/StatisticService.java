package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.configuration.AppContestants;
import org.laban.learning.spring.lessonfinal.mapper.StatisticMapper;
import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.model.kafka.HotelRoomBookedEvent;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.laban.learning.spring.lessonfinal.model.statistic.BookingStatisticRecord;
import org.laban.learning.spring.lessonfinal.model.statistic.UserRegistrationStatisticRecord;
import org.laban.learning.spring.lessonfinal.repository.statistic.BookingStatisticRecordRepository;
import org.laban.learning.spring.lessonfinal.repository.statistic.UserRegistrationStatisticRecordRepository;
import org.laban.learning.spring.lessonfinal.utils.io.csv.CSVHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class StatisticService {
    @Value("${app.kafka.kafkaUserRegistrationTopic}")
    private String kafkaUserRegistrationTopic;
    @Value("${app.kafka.kafkaHotelRoomBookingTopic}")
    private String kafkaHotelRoomBookingTopic;

    @Value("${app.storage.statistic.files.userRegistrations}")
    private String userRegistrationsFile;
    @Value("${app.storage.statistic.files.bookings}")
    private String bookingsFile;

    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;
    private final KafkaTemplate<String, HotelRoomBookedEvent> hotelRoomBookedTemplate;

    private final BookingStatisticRecordRepository bookingStatisticRepository;
    private final UserRegistrationStatisticRecordRepository userRegistrationStatisticRecordRepository;
    private final StatisticMapper statisticMapper;

    private final FileSystemStatisticStorageService fileStorageService;

    public void sendUserRegistered(User user) {
        userRegisteredTemplate.send(kafkaUserRegistrationTopic, UserRegisteredEvent.builder()
                .userId(user.getId())
                .timestamp(Instant.now())
                .build());
    }

    @Transactional
    public void onUserRegistered(UserRegisteredEvent event) {
        userRegistrationStatisticRecordRepository.save(
                statisticMapper.eventToUserRegistrationStatisticRecord(event));
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
        bookingStatisticRepository.save(statisticMapper.eventToBookingStatisticRecord(event));
    }

    @Transactional(readOnly = true)
    public Resource generateAndLoadUserRegistrationStatistic() throws IOException {
        CSVHelper.printCsvFile(
                CSVHelper.pageableContentProvider(userRegistrationStatisticRecordRepository,
                        AppContestants.DEFAULT_PAGE_SIZE),
                (printer, record) -> {
                    printer.print(record.getUserId());
                    printer.print(record.getTimestamp());
                },
                fileStorageService.openOutputStreamFor(userRegistrationsFile),
                UserRegistrationStatisticRecord.Fields.userId, UserRegistrationStatisticRecord.Fields.timestamp
        );
        return fileStorageService.loadAsResource(userRegistrationsFile);
    }

    @Transactional(readOnly = true)
    public Resource generateAndLoadBookingsStatistic() throws IOException {
        CSVHelper.printCsvFile(
                CSVHelper.pageableContentProvider(bookingStatisticRepository,
                        AppContestants.DEFAULT_PAGE_SIZE),
                (printer, record) -> {
                    printer.print(record.getBookingId());
                    printer.print(record.getUserId());
                    printer.print(record.getHotelRoomId());
                    printer.print(record.getStartBooking());
                    printer.print(record.getEndBooking());
                    printer.print(record.getTimestamp());
                },
                fileStorageService.openOutputStreamFor(bookingsFile),
                BookingStatisticRecord.Fields.bookingId,
                BookingStatisticRecord.Fields.userId,
                BookingStatisticRecord.Fields.hotelRoomId,
                BookingStatisticRecord.Fields.startBooking,
                BookingStatisticRecord.Fields.endBooking,
                BookingStatisticRecord.Fields.timestamp
        );
        return fileStorageService.loadAsResource(bookingsFile);
    }
}
