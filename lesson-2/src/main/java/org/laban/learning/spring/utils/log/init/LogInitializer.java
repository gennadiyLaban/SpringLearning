package org.laban.learning.spring.utils.log.init;

import org.laban.learning.spring.utils.log.LogFactoryCreator;
import org.laban.learning.spring.utils.log.log4j.Log4jFactoryCreator;

public class LogInitializer {
    private LogInitializer() {}

    public static LogFactoryCreator initialize() {
        return new Log4jFactoryCreator();
    }
}
