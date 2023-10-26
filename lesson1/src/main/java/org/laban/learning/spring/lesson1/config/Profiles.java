package org.laban.learning.spring.lesson1.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

public @interface Profiles {
    String DEFAULT = "default";
    String INIT = "init";

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Profile(DEFAULT)
    @interface Default {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Profile(INIT)
    @interface Init {
    }
}
