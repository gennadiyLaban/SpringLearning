package org.laban.learning.spring.utils.log;

import org.springframework.lang.NonNull;

public interface LogFactoryCreator {
    Logger createLogger(@NonNull Class<?> clazz);
}
