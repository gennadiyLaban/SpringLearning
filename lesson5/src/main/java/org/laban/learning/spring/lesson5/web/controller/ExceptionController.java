package org.laban.learning.spring.lesson5.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson5.exception.BookByNameAndAuthorNotFound;
import org.laban.learning.spring.lesson5.exception.BookNotFoundException;
import org.laban.learning.spring.lesson5.exception.CategoryNameNotFound;
import org.laban.learning.spring.lesson5.utils.ErrorResponseUtils;
import org.laban.learning.spring.lesson5.web.dto.ErrorBodyDTO;
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

    @ExceptionHandler({BookByNameAndAuthorNotFound.class})
    public ResponseEntity<ErrorBodyDTO> userNotFoundException(
            BookByNameAndAuthorNotFound exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format(
                "Book with name={0} and author={1} not found!", exception.getName(), exception.getAuthor());
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> categoryNotFoundException(
            BookNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Book with id={0} not found!", exception.getBookId());
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({CategoryNameNotFound.class})
    public ResponseEntity<ErrorBodyDTO> categoryNotFoundException(
            CategoryNameNotFound exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Category {0} not found!", exception.getCategoryName());
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }
}
