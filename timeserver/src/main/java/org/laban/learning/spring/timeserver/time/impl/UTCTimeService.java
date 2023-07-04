package org.laban.learning.spring.timeserver.time.impl;

import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.laban.learning.spring.timeserver.time.TimeService;
import org.laban.learning.spring.util.time.Now;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod", "default"})
@Slf4j
public class UTCTimeService implements TimeService {
    private final DateTimeFormatter dateTimeFormatter;

    public UTCTimeService(TimeProviderProperties properties) {
        this.dateTimeFormatter = DateTimeFormatter
                .ofPattern(properties.getFormat());
    }

    @Override
    public void printCurrentTime() {
        log.info("Current time in UTC: %s".formatted(Now.offsetDateTime().format(dateTimeFormatter)));
    }
}
