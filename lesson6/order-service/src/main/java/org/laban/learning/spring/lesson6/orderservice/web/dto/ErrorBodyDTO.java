package org.laban.learning.spring.lesson6.orderservice.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorBodyDTO {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
