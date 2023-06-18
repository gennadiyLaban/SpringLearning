package org.laban.learning.spring.timeserver.time.impl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.laban.learning.spring.timeserver.time.TimeService;
import org.laban.learning.spring.timeserver.time.clock.Now;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class ServerLocationTimeService implements TimeService {
    private final Logger logger = Logger.getLogger(ServerLocationTimeService.class.getName());

    private final DateTimeFormatter dateTimeFormatter;
    private final ZoneId serverLocationZoneId;

    public ServerLocationTimeService(TimeProviderProperties properties)  {
        this.serverLocationZoneId = ZoneId.systemDefault();
        this.dateTimeFormatter = DateTimeFormatter
                .ofPattern(properties.getFormat())
                .withZone(serverLocationZoneId);
    }

    @Override
    public void printCurrentTime() {
        logger.info("Current time in %s: %s".formatted(
                serverLocationZoneId.toString(),
                Now.offsetDateTime().format(dateTimeFormatter))
        );
    }
}
