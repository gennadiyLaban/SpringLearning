package org.laban.learning.spring.lesson7.withprotection.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson7.withprotection.utils.ErrorResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements ServerAuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(
            ServerWebExchange exchange,
            AuthenticationException ex
    ) {
        return Mono.defer(() -> {
            var response = exchange.getResponse();
            var request = exchange.getRequest();
            log.error("Unauthorized error {}", ex.getMessage());

            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            try {
                var bytes = objectMapper.writeValueAsBytes(ErrorResponseUtils.buildErrorBody(
                        HttpStatus.UNAUTHORIZED, request, ex.getMessage()
                ));
                return response.writeWith(
                        Mono.just(response.bufferFactory().wrap(bytes))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
