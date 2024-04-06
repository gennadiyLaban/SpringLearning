package org.laban.learning.spring.lesson7.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TaskNotFoundException extends RuntimeException {
    private final String taskId;
}
