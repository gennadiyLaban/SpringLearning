package org.laban.learning.spring.lesson7.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson7.exception.TaskNotFoundException;
import org.laban.learning.spring.lesson7.exception.UserNotFoundException;
import org.laban.learning.spring.lesson7.utils.ErrorResponseUtils;
import org.laban.learning.spring.lesson7.web.dto.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorBodyDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
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
            HttpServletRequest request
    ) {
        log.error(
                MessageFormat.format("Uncatch exception! path: ''{0}''; method: ''{1}''",
                        request.getServletPath(), request.getMethod()),
                exception
        );
        return ErrorResponseUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR, request, exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> handleUserNotFoundExceptionException(
            UserNotFoundException exception,
            HttpServletRequest request
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
            HttpServletRequest request
    ) {
        return ErrorResponseUtils.buildError(
                HttpStatus.NOT_FOUND,
                request,
                MessageFormat.format("Task with id=''{0}'' not found!", exception.getTaskId())
        );
    }
}
