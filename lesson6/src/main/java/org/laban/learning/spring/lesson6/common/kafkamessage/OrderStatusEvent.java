package org.laban.learning.spring.lesson6.common.kafkamessage;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Builder
@Getter
public class OrderStatusEvent {
    private final String status;
    private final Instant date;
}
