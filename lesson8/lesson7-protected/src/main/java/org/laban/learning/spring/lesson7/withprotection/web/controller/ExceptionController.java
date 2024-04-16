package org.laban.learning.spring.lesson7.withprotection.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson7.withprotection.exception.TaskNotFoundException;
import org.laban.learning.spring.lesson7.withprotection.exception.UserNotFoundException;
import org.laban.learning.spring.lesson7.withprotection.utils.ErrorResponseUtils;
import org.laban.learning.spring.lesson7.withprotection.web.dto.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorBodyDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            ServerHttpRequest request
    ) {
        String errorMsg = exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getDefaultMessage()))
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse(exception.getMessage());
        return ErrorResponseUtils.buildError(HttpStatus.BAD_REQUEST, request, errorMsg);
    }

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<ErrorBodyDTO> handleWebExchangeBindException(
            WebExchangeBindException exception,
            ServerHttpRequest request
    ) {
        String errorMsg = exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getDefaultMessage()))
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse(exception.getMessage());
        return ErrorResponseUtils.buildError(HttpStatus.BAD_REQUEST, request, errorMsg);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorBodyDTO> handleUncatchedRuntimeException(
            RuntimeException exception,
            ServerHttpRequest request
    ) {
        log.error(
                MessageFormat.format("Uncatch exception! path: ''{0}''; method: ''{1}''",
                        request.getPath().value(), request.getMethod()),
                exception
        );
        return ErrorResponseUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR, request, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorBodyDTO> handleAccessDeniedException(
            AccessDeniedException exception,
            ServerHttpRequest request
    ) {
        log.error(MessageFormat.format("Access Denied: path ''{0}''; method: ''{1}''",
                request.getPath().value(), request.getMethod()));
        return ErrorResponseUtils.buildError(HttpStatus.UNAUTHORIZED, request, exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> handleUserNotFoundExceptionException(
            UserNotFoundException exception,
            ServerHttpRequest request
    ) {
        return ErrorResponseUtils.buildError(
                HttpStatus.NOT_FOUND,
                request,
                MessageFormat.format("User with id=''{0}'' not found!", exception.getUserId())
        );
    }

    @ExceptionHandler({TaskNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> handleUserNotFoundExceptionException(
            TaskNotFoundException exception,
            ServerHttpRequest request
    ) {
        return ErrorResponseUtils.buildError(
                HttpStatus.NOT_FOUND,
                request,
                MessageFormat.format("Task with id=''{0}'' not found!", exception.getTaskId())
        );
    }
}
