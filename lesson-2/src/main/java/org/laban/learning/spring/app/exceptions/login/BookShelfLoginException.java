package org.laban.learning.spring.app.exceptions.login;

import org.laban.learning.spring.app.exceptions.DomainException;

public class BookShelfLoginException extends DomainException {
    public BookShelfLoginException(String message) {
        super(message);
    }
}
