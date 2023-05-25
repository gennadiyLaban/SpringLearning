package org.laban.learning.spring.app.config;

import org.laban.learning.spring.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class EnvironmentConfig {
    @Bean("environment")
    @Lazy
    public Environment environment() {
        return Environment.getInstance();
    }
}
