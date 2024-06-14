package org.laban.learning.spring.lessonfinal;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.repository.statistic.BookingStatisticRecordRepository;
import org.laban.learning.spring.lessonfinal.repository.statistic.UserRegistrationStatisticRecordRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MongoDbDataInitializer {
    private final BookingStatisticRecordRepository bookingStatisticRecordRepository;
    private final UserRegistrationStatisticRecordRepository userRegistrationStatisticRecordRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void clearData() {
        bookingStatisticRecordRepository.deleteAll();
        userRegistrationStatisticRecordRepository.deleteAll();
    }
}
