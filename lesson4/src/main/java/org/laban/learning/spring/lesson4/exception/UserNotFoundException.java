package org.laban.learning.spring.lesson4.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class UserNotFoundException extends RuntimeException {
    private Long userId;
    private List<Long> userIds;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }

    public UserNotFoundException(List<Long> userIds) {
        this.userIds = userIds;
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
