package org.laban.learning.spring.lesson1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.laban.learning.spring.lesson1")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
