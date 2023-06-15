package org.laban.learning.spring.utils.log;

import org.springframework.lang.NonNull;

public class LogFactory {
    private LogFactory() {}

    @NonNull
    public static Logger getLogger(@NonNull Class<?> clazz) {
        return LogFactoryProvider.logCreator().createLogger(clazz);
    }
}
