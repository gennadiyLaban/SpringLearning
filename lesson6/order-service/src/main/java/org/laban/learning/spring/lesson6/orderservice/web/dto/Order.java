package org.laban.learning.spring.lesson6.orderservice.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Order {
    @NotEmpty
    private String product;

    @NotNull
    @Positive
    private Integer quantity;
}
