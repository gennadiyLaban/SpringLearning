package org.laban.learning.spring.app.services;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value= RetentionPolicy.SOURCE)
public @interface Qualifiers {
    String BOOKS = "books";
}
