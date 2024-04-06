package org.laban.learning.spring.lesson7.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.exception.UserNotFoundException;
import org.laban.learning.spring.lesson7.mapper.UserMapper;
import org.laban.learning.spring.lesson7.repository.UserRepository;
import org.laban.learning.spring.lesson7.web.dto.UserDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<UserDTO> findUserById(@Nonnull String userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserDTO)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)));
    }

}
