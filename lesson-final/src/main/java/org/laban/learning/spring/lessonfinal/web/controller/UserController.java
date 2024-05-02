package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.UserService;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

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
}
