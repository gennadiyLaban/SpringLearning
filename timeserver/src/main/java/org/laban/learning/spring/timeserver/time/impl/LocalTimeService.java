package org.laban.learning.spring.timeserver.time.impl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.laban.learning.spring.timeserver.time.TimeService;
import org.laban.learning.spring.timeserver.time.clock.Now;

public class LocalTimeService implements TimeService {
    private final Logger logger = Logger.getLogger(LocalTimeService.class.getName());

    private final DateTimeFormatter dateTimeFormatter;

    public LocalTimeService(TimeProviderProperties properties)  {
        this.dateTimeFormatter = DateTimeFormatter
                .ofPattern(properties.getFormat())
                .withZone(ZoneId.systemDefault());
    }

    @Override
    public void printCurrentTime() {
        logger.info("Current time in %s: %s".formatted(
                Now.clock().getZone().toString(),
                Now.offsetDateTime().format(dateTimeFormatter))
        );
    }
}
