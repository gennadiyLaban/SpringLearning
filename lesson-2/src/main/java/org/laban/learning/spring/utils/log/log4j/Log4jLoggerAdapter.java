package org.laban.learning.spring.utils.log.log4j;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Log4jLoggerAdapter implements Logger {
    private final org.apache.logging.log4j.Logger logger;
    private final org.apache.logging.log4j.Marker marker;

    public Log4jLoggerAdapter(
            org.apache.logging.log4j.Logger logger,
            org.apache.logging.log4j.Marker marker
    ) {
        this.logger = logger;
        this.marker = marker;
    }

    @Override
    public void info(@NonNull String message) {
        logger.atInfo().withMarker(marker).withLocation(location()).log(message);
    }

    @Override
    public void info(String message, Object... params) {
        logger.atInfo().withMarker(marker).withLocation(location()).log(message, params);
    }

    @Override
    public void error(String message) {
        logger.atError().withMarker(marker).withLocation(location()).log(message);
    }

    @Override
    public void error(String message, Throwable th) {
        logger.atError().withMarker(marker).withLocation(location()).withThrowable(th).log(message);
    }

    @Override
    public void debug(String message) {
        logger.atDebug().withMarker(marker).withLocation(location()).log(message);
    }

    @Override
    public void debug(String message, Object... params) {
        logger.atDebug().withMarker(marker).withLocation(location()).log(message, params);
    }

    private org.apache.logging.log4j.LogBuilder atInfo() {
        return logger.atInfo().withMarker(marker).withLocation(location());
    }

    private org.apache.logging.log4j.LogBuilder atError() {
        return logger.atError().withMarker(marker).withLocation(location());
    }

    private org.apache.logging.log4j.LogBuilder atError(Throwable th) {
        return atError().withMarker(marker).withThrowable(th);
    }

    private org.apache.logging.log4j.LogBuilder atDebug() {
        return logger.atDebug().withMarker(marker).withLocation(location());
    }

    @Nullable
    private StackTraceElement location() {
        return StackLocatorUtil.getStackTraceElement(3);
    }
}
