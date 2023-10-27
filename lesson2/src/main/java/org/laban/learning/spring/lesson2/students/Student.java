package org.laban.learning.spring.lesson2.students;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
