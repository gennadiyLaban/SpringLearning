package org.laban.learning.spring.lesson7.withprotection.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.withprotection.service.UserService;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserDTO;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserListDTO;
import org.laban.learning.spring.lesson7.withprotection.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping
    public Mono<ResponseEntity<UserDTO>> findUserById(
            @Valid @NotBlank @RequestParam String userId
    ) {
        return userService.findUserDTOById(userId)
                .map(ResponseEntity::ok);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/list")
    public Mono<ResponseEntity<UserListDTO>> findAllUsers() {
        return userService.findAllUserDTOs()
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createUser(
            @Validated(ValidationGroup.Create.class)
            @RequestBody
            UserDTO userDTO
    ) {
        return userService.createUserByDTO(userDTO)
                        .map(userId -> ResponseEntity
                                .created(URI.create("/api/v1/user?userId=" + encode(userId)))
                                .build());
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @PutMapping
    public Mono<ResponseEntity<Void>> updateUser(
            @Validated(ValidationGroup.Update.class)
            @RequestBody
            UserDTO userDTO
    ) {
        return userService.updateUserByDTO(userDTO)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteUser(@RequestParam String userId) {
        return userService.deleteUserById(userId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
