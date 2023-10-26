package org.laban.learning.spring.lesson2;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellInterface {
    @Value("${my.prop}")
    private String fooProperty;

    @ShellMethod
    public String echo(String param) {
        return MessageFormat.format("my.prop={0}; echo: {1}", fooProperty, param);
    }

}
