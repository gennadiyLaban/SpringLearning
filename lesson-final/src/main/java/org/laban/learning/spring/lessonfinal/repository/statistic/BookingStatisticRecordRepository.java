package org.laban.learning.spring.lessonfinal.repository.statistic;

import org.laban.learning.spring.lessonfinal.model.statistic.BookingStatisticRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatisticRecordRepository extends MongoRepository<BookingStatisticRecord, Long> {
}
