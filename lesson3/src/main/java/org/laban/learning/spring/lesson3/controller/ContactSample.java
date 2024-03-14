package org.laban.learning.spring.lesson3.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;
import org.laban.learning.spring.lesson3.validation.ValidationGroup;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactSample {
    @NotNull(groups = { ValidationGroup.Update.class })
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "^\\+\\d+")
    private String phone;
}