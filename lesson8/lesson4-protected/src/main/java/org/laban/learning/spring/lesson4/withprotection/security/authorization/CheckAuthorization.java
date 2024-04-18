package org.laban.learning.spring.lesson4.withprotection.security.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckAuthorization {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface UserGet {
        String paramName();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface UserList {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface UserUpdate {
        String paramName();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface UserDelete {
        String paramName();
    }
}
