package org.laban.learning.spring.lesson2.events;

import lombok.RequiredArgsConstructor;
import org.jline.terminal.Terminal;
import org.laban.learning.spring.lesson2.MessageBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StudentEventListener {
    private final Terminal terminal;

    @EventListener
    public void studentCreatedListener(StudentCreatedEvent event) {
        var builder = new MessageBuilder();
        builder.border().formatMsg("Student with id:{0} was created", event.getId()).newLine();
        terminal.writer().print(builder);
    }

    @EventListener
    public void studentDeletedListener(StudentDeletedEvent event) {
        var builder = new MessageBuilder();
        builder.border().formatMsg("Student with id:{0} was deleted", event.getId()).newLine();
        terminal.writer().print(builder);
    }
}
