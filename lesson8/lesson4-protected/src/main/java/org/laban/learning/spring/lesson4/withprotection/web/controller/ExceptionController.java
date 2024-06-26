package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson4.withprotection.exception.*;
import org.laban.learning.spring.lesson4.withprotection.utils.ErrorResponseUtils;
import org.laban.learning.spring.lesson4.withprotection.web.dto.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorBodyDTO> handleAccessDeniedException(
            AccessDeniedException exception,
            HttpServletRequest request
    ) {
        log.error(
                MessageFormat.format("Access denied! path: ''{0}''; method: ''{1}''",
                        request.getServletPath(), request.getMethod()),
                exception
        );
        return ErrorResponseUtils.buildError(HttpStatus.UNAUTHORIZED, request, exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> userNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = exception.getUserId() != null
                ? MessageFormat.format("User with id={0} not found!", exception.getUserId())
                : "A few users not found!";
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> categoryNotFoundException(
            CategoryNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = exception.getCategoryId() != null
                ? MessageFormat.format("Category with id={0} not found!", exception.getCategoryId())
                : "Categories not found!";
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> categoryNotFoundException(
            PostNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Post with id={0} not found!", exception.getPostId());
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({CustomAccessDeniedException.class})
    public ResponseEntity<ErrorBodyDTO> accessDeniedException(
            CustomAccessDeniedException exception,
            HttpServletRequest request
    ) {
        return ErrorResponseUtils.buildError(HttpStatus.UNAUTHORIZED, request, exception.getMessage());
    }

    @ExceptionHandler({CommentNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> commentNotFoundException(
            CommentNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Comment with id={0} not found!", exception.getCommentId());
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }
}
