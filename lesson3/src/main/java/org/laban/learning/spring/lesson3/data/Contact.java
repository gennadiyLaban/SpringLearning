package org.laban.learning.spring.lesson3.data;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
@Builder
@Getter
public class Contact {
    @With
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
}