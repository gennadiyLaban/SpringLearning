package org.laban.learning.spring.lesson5.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.web.validation.group.ValidationGroup;

import java.io.Serializable;

@RequiredArgsConstructor
@Builder
@Getter
public class BookDTO implements Serializable {
    @NotNull(groups = ValidationGroup.Update.class)
    @Null(groups = ValidationGroup.Create.class)
    private final Long id;

    @NotBlank
    private final String author;

    @NotBlank
    private final String name;

    @NotBlank
    private final String category;
}
