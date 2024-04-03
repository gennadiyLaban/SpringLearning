package org.laban.learning.spring.lesson6.common.kafkamessage;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class OrderEvent {
    private final String product;
    private final Integer quantity;
}
