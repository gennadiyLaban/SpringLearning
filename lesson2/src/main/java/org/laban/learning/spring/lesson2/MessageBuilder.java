package org.laban.learning.spring.lesson2;

import java.text.MessageFormat;

import org.laban.learning.spring.lesson2.students.Student;

public class MessageBuilder {
    private final StringBuilder builder = new StringBuilder();

    public MessageBuilder border() {
        builder.append("== ");
        return this;
    }

    public MessageBuilder divider() {
        builder.append("====================================================================").append('\n');
        return this;
    }

    public MessageBuilder newLine() {
        builder.append('\n');
        return this;
    }

    public MessageBuilder formatMsg(String pattern, Object ... arguments) {
        builder.append(MessageFormat.format(pattern,arguments));
        return this;
    }

    public MessageBuilder msg(String msg) {
        builder.append(msg);
        return this;
    }

    public MessageBuilder student(Student student) {
        builder.append(MessageFormat.format("id:{0} | {1} {2} | age:{3}",
                student.getId(), student.getFirstName(), student.getLastName(), student.getAge()));
        return this;
    }

    public String toString() {
        return builder.toString();
    }

}
