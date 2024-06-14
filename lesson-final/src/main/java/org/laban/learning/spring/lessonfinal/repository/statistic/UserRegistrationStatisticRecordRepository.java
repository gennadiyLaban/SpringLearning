package org.laban.learning.spring.lessonfinal.repository.statistic;

import org.laban.learning.spring.lessonfinal.model.statistic.UserRegistrationStatisticRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationStatisticRecordRepository extends MongoRepository<UserRegistrationStatisticRecord, Long> {
}
