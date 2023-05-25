package org.laban.learning.spring.utils.log;

import org.laban.learning.spring.utils.log.init.LogInitializer;
import org.springframework.lang.NonNull;

public class LogFactoryProvider {
    @SuppressWarnings({ "java:S3077", "java:S3008"})
    private static volatile LogFactoryCreator INSTANCE;

    private LogFactoryProvider() {}

    @NonNull
    public static LogFactoryCreator logCreator() {
        if (INSTANCE == null) {
            synchronized (LogFactoryProvider.class) {
                if (INSTANCE == null) {
                    var creator = LogInitializer.initialize();
                    INSTANCE = creator;
                }
            }
        }
        return INSTANCE;
    }
}
