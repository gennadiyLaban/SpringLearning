package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.UserService;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<UserListDTO> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUserDTOs());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Validated(ValidationGroup.Create.class)
            @RequestBody
            UserDTO userDTO
    ) {
        var createdId = userService.createUser(userDTO);
        return ResponseEntity.created(URI.create("api/v1/user/" + createdId)).build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @Validated(ValidationGroup.Update.class)
            @RequestBody
            UserDTO userDTO
    ) {
        userService.updateUser(userDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
