package org.laban.learning.spring.lessonfinal.web.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lessonfinal.exception.*;
import org.laban.learning.spring.lessonfinal.utils.ErrorResponseUtils;
import org.laban.learning.spring.lessonfinal.web.dto.ErrorBodyDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
        String errorMsg = extractFieldError(exception);
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = extractGlobalError(exception);
        }
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = exception.getMessage();
        }

        return ErrorResponseUtils.buildError(HttpStatus.BAD_REQUEST, request, errorMsg);
    }

    @Nullable
    private String extractFieldError(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getDefaultMessage()))
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse(null);
    }

    @Nullable
    private String extractGlobalError(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors()
                .stream()
                .filter(error -> StringUtils.isNotEmpty(error.getDefaultMessage()))
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(null);
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
    public ResponseEntity<ErrorBodyDTO> hotelNotFoundException(
            HotelNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Hotel with id={0} not found!", exception.getHotelId());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({HotelRoomNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> hotelRoomNotFoundException(
            HotelRoomNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Hotel room with id={0} not found!", exception.getRoomId());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> userNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("User with id={0} not found!", exception.getUserId());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({BookingNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> bookingNotFoundException(
            BookingNotFoundException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Booking with id={0} not found!", exception.getBookingId());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request, message);
    }

    @ExceptionHandler({BookingDatesAlreadyBookedException.class})
    public ResponseEntity<ErrorBodyDTO> bookingNotFoundException(
            BookingDatesAlreadyBookedException exception,
            HttpServletRequest request
    ) {
        var message = MessageFormat.format("Booking dates from {0} to {1} are already booked",
                exception.getStartDate(), exception.getEndDate());
        log.error(message, exception);
        return ErrorResponseUtils.buildError(HttpStatus.BAD_REQUEST, request, message);
    }
}
