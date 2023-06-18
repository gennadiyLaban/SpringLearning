package org.laban.learning.spring.timeserver;

import org.laban.learning.spring.timeserver.config.time.TimeProviderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:custom.properties")
@EnableConfigurationProperties(value = { TimeProviderProperties.class })
public class SpringLearningTimeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearningTimeServerApplication.class, args);
    }

}
