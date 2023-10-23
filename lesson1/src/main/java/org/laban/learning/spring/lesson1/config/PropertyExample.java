package org.laban.learning.spring.lesson1.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
@EqualsAndHashCode
public class PropertyExample {
    @Value("${my.property}")
    private String myProperty;
}
