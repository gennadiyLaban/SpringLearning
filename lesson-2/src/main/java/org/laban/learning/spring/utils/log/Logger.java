package org.laban.learning.spring.utils.log;

import org.springframework.lang.NonNull;

public interface Logger {
    void info(@NonNull String message);
    void info(String message, Object ... args);

    void error(String message);

    void error(String message, Throwable th);

    void debug(String message);

    void debug(String message, Object... params);
}
