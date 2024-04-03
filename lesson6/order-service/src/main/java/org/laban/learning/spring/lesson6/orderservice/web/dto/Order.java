package org.laban.learning.spring.lesson6.orderservice.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson6.common.kafkamessage.OrderEvent;

@RequiredArgsConstructor
@Builder
@Getter
public class Order {
    @NotEmpty
    private String product;

    @NotNull
    @Positive
    private Integer quantity;

    public OrderEvent toEvent() {
        return OrderEvent.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }
}
