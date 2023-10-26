package org.laban.learning.spring.lesson1.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
    private String name;
    private String phone;
    private String email;
}
