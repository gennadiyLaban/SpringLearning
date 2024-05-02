package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.UserService;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
