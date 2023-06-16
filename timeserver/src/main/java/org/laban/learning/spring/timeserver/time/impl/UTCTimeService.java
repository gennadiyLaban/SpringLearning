package org.laban.learning.spring.timeserver.time.impl;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.laban.learning.spring.timeserver.time.TimeService;
import org.laban.learning.spring.timeserver.time.clock.Now;
import org.springframework.stereotype.Component;

@Component
public class UTCTimeService implements TimeService {
    private final Logger logger = Logger.getLogger(UTCTimeService.class.getName());
    private final DateTimeFormatter dateTimeFormatter;

    public UTCTimeService() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'xxx");
    }

    @Override
    public void printCurrentTime() {
        logger.info("Current time: %s".formatted(Now.offsetDateTime().format(dateTimeFormatter)));
    }
}
