package org.laban.learning.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Integer id;

    @NotEmpty
    private String author;

    @NotEmpty
    private String title;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer size;
}
