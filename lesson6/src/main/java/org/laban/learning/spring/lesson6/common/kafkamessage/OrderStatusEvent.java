package org.laban.learning.spring.lesson6.common.kafkamessage;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@RequiredArgsConstructor
@Builder
@Getter
@ToString
public class OrderStatusEvent {
    private final String status;
    private final Instant date;
}
