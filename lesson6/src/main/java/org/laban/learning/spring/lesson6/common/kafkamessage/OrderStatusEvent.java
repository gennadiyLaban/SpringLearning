package org.laban.learning.spring.lesson6.common.kafkamessage;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class OrderStatusEvent {
    private String status;
    private Instant date;
}
