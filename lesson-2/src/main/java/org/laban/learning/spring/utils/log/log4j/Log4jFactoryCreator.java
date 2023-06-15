package org.laban.learning.spring.utils.log.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.laban.learning.spring.utils.log.LogFactoryCreator;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.lang.NonNull;

public class Log4jFactoryCreator implements LogFactoryCreator {
    private final Marker marker = MarkerManager.getMarker(Log4jConfigurationFactory.USR_MARKER);

    @Override
    public Logger createLogger(@NonNull Class<?> clazz) {
        return new Log4jLoggerAdapter(LogManager.getLogger(clazz), marker);
    }


}
