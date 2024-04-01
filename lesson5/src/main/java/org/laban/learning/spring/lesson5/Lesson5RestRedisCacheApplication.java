package org.laban.learning.spring.lesson5;

import org.laban.learning.spring.lesson5.properties.AppCacheProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        AppCacheProperties.class
})
public class Lesson5RestRedisCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(Lesson5RestRedisCacheApplication.class, args);
    }
}
