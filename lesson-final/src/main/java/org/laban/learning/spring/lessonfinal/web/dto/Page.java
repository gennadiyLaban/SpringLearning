package org.laban.learning.spring.lessonfinal.web.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Page {
    @PositiveOrZero
    private Integer number;

    @Positive
    private Integer size;
}
