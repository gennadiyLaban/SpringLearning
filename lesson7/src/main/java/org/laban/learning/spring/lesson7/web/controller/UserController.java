package org.laban.learning.spring.lesson7.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.service.UserService;
import org.laban.learning.spring.lesson7.web.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<UserDTO>> findUserById(
            @PathVariable String userId
    ) {
        return userService.findUserById(userId)
                .map(ResponseEntity::ok);
    }

}
