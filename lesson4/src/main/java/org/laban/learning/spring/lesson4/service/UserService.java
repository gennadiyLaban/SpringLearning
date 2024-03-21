package org.laban.learning.spring.lesson4.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.exception.NotFoundException;
import org.laban.learning.spring.lesson4.model.User;
import org.laban.learning.spring.lesson4.repository.UserRepository;
import org.laban.learning.spring.lesson4.web.dto.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findUserDTOById(@Nonnull Long id) {
        return findUserById(id)
                .map(this::convertUserToDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public UserListDTO findAll(@Nonnull UserListRequest request) {
        var page = findAll(Pageable
                .ofSize(request.getSize())
                .withPage(request.getPage()));

        return UserListDTO.builder()
                .users(page.getContent().stream()
                        .map(this::convertUserToDTO)
                        .toList())
                .page(page.getNumber())
                .pageSize(page.getSize())
                .pageCount(page.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(@Nonnull Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    private UserDTO convertUserToDTO(@Nonnull User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }


}
