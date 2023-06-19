package org.laban.learning.spring.timeserver;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppStartPrinter implements CommandLineRunner {

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
        log.info("Running application: %s".formatted(appName));
        log.info("Application profile: %s".formatted(properties.getProfile()));
        log.info(properties.getDescription());
        log.info(welcomeMessage);
    }
}
