package org.laban.learning.spring.lesson6.common.kafkamessage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class OrderEvent {
    private String product;
    private Integer quantity;
}
