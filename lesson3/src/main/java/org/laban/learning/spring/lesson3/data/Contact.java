package org.laban.learning.spring.lesson3.data;

import lombok.*;

@RequiredArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class Contact {
    @With
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
}