package org.laban.learning.spring.lesson2.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StudentCreatedEvent extends ApplicationEvent {
    private final long id;

    public StudentCreatedEvent(Object source, long id) {
        super(source);
        this.id = id;
    }
}
