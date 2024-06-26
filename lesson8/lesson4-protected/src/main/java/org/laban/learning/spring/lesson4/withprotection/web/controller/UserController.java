package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.service.UserService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;

@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

//    @PreAuthorize("@userAuthManager.hasPermissionForGetById(#userDetails, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> userById(
            @PathVariable @NotNull Long id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }

//    @PreAuthorize("@userAuthManager.hasPermissionForGetUserList(#userDetails)")
    @GetMapping("/list")
    public ResponseEntity<UserListDTO> userList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                userService.findAllByDTO(Pageable.ofSize(size).withPage(page))
        );
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody @Validated({ValidationGroup.Create.class})
            UserDTO userDTO
    ) {
        var createdId = userService.createUserByDTO(userDTO);
        return ResponseEntity.created(
                URI.create(MessageFormat.format("/api/v1/user/{0}", createdId))
        ).build();
    }

//    @PreAuthorize("@userAuthManager.hasPermissionForUpdate(#userDetails, #userDTO)")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @RequestBody
            @Validated(value = { ValidationGroup.Update.class })
            UserDTO userDTO,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        userService.updateUserByDTO(userDTO);
        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("@userAuthManager.hasPermissionForDelete(#userDetails, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
