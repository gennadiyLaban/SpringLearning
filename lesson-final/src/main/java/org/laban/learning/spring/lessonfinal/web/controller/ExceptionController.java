package org.laban.learning.spring.lessonfinal.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lessonfinal.exception.HotelNotFoundException;
import org.laban.learning.spring.lessonfinal.utils.ErrorResponseUtils;
import org.laban.learning.spring.lessonfinal.web.dto.ErrorBodyDTO;
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorBodyDTO> handleUncatchedException(
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

//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<ErrorBodyDTO> handleAccessDeniedException(
//            AccessDeniedException exception,
//            HttpServletRequest request
//    ) {
//        log.error(
//                MessageFormat.format("Access denied! path: ''{0}''; method: ''{1}''",
//                        request.getServletPath(), request.getMethod()),
//                exception
//        );
//        return ErrorResponseUtils.buildError(HttpStatus.UNAUTHORIZED, request, exception.getMessage());
//    }

    @ExceptionHandler({HotelNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> userNotFoundException(
            HotelNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Hotel with id={0} not found!", exception.getHotelId());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }
}
