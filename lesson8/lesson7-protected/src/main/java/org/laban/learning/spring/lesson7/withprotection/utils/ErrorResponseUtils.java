package org.laban.learning.spring.lesson7.withprotection.utils;

import org.laban.learning.spring.lesson7.withprotection.web.dto.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.Instant;

public class ErrorResponseUtils {
    private ErrorResponseUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static ErrorBodyDTO buildErrorBody(HttpStatus status, ServerHttpRequest request, String message) {
        return ErrorBodyDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getPath().value())
                .build();
    }

    public static ResponseEntity<ErrorBodyDTO> buildError(HttpStatus status, ServerHttpRequest request, String message) {
        return ResponseEntity
                .status(status)
                .body(buildErrorBody(status, request, message));
    }

}
