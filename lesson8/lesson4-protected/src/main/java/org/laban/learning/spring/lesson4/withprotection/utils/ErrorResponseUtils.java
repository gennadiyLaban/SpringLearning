package org.laban.learning.spring.lesson4.withprotection.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.laban.learning.spring.lesson4.withprotection.web.dto.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ErrorResponseUtils {
    private ErrorResponseUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static ErrorBodyDTO buildErrorBody(HttpStatus status, HttpServletRequest request, String message) {
        return ErrorBodyDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getServletPath())
                .build();
    }

    public static ResponseEntity<ErrorBodyDTO> buildError(HttpStatus status, HttpServletRequest request, String message) {
        return ResponseEntity
                .status(status)
                .body(buildErrorBody(status, request, message));
    }

}
