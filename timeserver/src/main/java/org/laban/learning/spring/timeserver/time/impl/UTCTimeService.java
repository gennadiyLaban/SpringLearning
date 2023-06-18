package org.laban.learning.spring.timeserver.time.impl;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.laban.learning.spring.timeserver.time.TimeService;
import org.laban.learning.spring.timeserver.time.clock.Now;
import org.springframework.stereotype.Component;

@Component
public class UTCTimeService implements TimeService {
    private final Logger logger = Logger.getLogger(UTCTimeService.class.getName());
    private final DateTimeFormatter dateTimeFormatter;

    public UTCTimeService(TimeProviderProperties properties) {
        this.dateTimeFormatter = DateTimeFormatter
                .ofPattern(properties.getFormat());
    }

    @Override
    public void printCurrentTime() {
        logger.info("Current time in UTC: %s".formatted(Now.offsetDateTime().format(dateTimeFormatter)));
    }
}
