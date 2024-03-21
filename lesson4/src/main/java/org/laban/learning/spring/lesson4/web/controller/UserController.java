package org.laban.learning.spring.lesson4.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson4.exception.UserNotFoundException;
import org.laban.learning.spring.lesson4.service.UserService;
import org.laban.learning.spring.lesson4.web.dto.ErrorBodyDTO;
import org.laban.learning.spring.lesson4.web.dto.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListRequest;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.time.Instant;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> userById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findUserDTOById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<UserListDTO> userList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(userService.findAll(
                UserListRequest.builder()
                        .page(page)
                        .size(size)
                        .build()));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody @Valid UserDTO userDTO,
            UriComponentsBuilder builder
    ) {
        var user = userService.createUserByDTO(userDTO);
        return ResponseEntity.created(
                builder.path("/user").path("/{id}").buildAndExpand(user.getId()).toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable
            @NotNull
            Long id,
            @RequestBody
            @Validated(value = { ValidationGroup.Update.class })
            UserDTO userDTO
    ) {
        userService.updateUserByDTO(userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NotNull Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<ErrorBodyDTO> userNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildError(HttpStatus.NOT_FOUND, request,
                MessageFormat.format("User with id={0} not found!", exception.getUserId()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorBodyDTO> handleException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String errorMsg = exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getDefaultMessage()))
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse(exception.getMessage());
        return buildError(HttpStatus.BAD_REQUEST, request, errorMsg);
    }

    private ResponseEntity<ErrorBodyDTO> buildError(HttpStatus status, HttpServletRequest request, String message) {
        return ResponseEntity
                .status(status)
                .body(ErrorBodyDTO.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .path(request.getServletPath())
                        .build());
    }
}
