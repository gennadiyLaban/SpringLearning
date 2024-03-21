package org.laban.learning.spring.lesson4.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.service.UserService;
import org.laban.learning.spring.lesson4.web.dto.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListRequest;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
}
