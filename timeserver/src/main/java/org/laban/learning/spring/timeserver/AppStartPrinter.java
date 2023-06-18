package org.laban.learning.spring.timeserver;

import java.util.logging.Logger;

import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartPrinter implements CommandLineRunner {
    private final Logger logger = Logger.getLogger(AppStartPrinter.class.getName());

    @Value("${spring.application.name}")
    private String appName;
    @Value("${custom.welcomeMessage}")
    private String welcomeMessage;
    private final TimeProviderProperties properties;

    public AppStartPrinter(TimeProviderProperties properties) {
        this.properties = properties;
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("Running application: %s".formatted(appName));
        logger.info("Application profile: %s".formatted(properties.getProfile()));
        logger.info(properties.getDescription());
        logger.info(welcomeMessage);
    }
}
