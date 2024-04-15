package org.laban.learning.spring.lesson7.withprotection.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.withprotection.exception.UserNotFoundException;
import org.laban.learning.spring.lesson7.withprotection.mapper.UserMapper;
import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.laban.learning.spring.lesson7.withprotection.repository.UserRepository;
import org.laban.learning.spring.lesson7.withprotection.utils.BeanUtils;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserDTO;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserListDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<UserDTO> findUserDTOById(@Nonnull String userId) {
        return getUserById(userId)
                .map(userMapper::userToUserDTO);
    }

    public Mono<User> getUserById(@Nonnull String userId) {
        return findUserById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)));
    }

    public Mono<User> findUserById(@Nonnull String userId) {
        return userRepository.findById(userId);
    }

    public Mono<UserListDTO> findAllUserDTOs() {
        return userRepository.findAll()
                .collectList()
                .map(userMapper::userListToUserListDTO);
    }

    public Mono<String> createUserByDTO(@Nonnull UserDTO userDTO) {
        return Mono.just(userDTO)
                .map(userMapper::userDTOtoUser)
                .flatMap(userRepository::save)
                .map(User::getId);
    }

    public Mono<Void> updateUserByDTO(@Nonnull UserDTO userDTO) {
        return Mono.just(userDTO)
                .map(userMapper::userDTOtoUser)
                .flatMap(upsertUser -> Mono.zip(
                        getUserById(upsertUser.getId()),
                        Mono.just(upsertUser)
                ))
                .map(users -> {
                    var existedUser = users.getT1();
                    var upsertUser = users.getT2();
                    BeanUtils.copyNonNullProperties(upsertUser, existedUser);
                    return existedUser;
                })
                .flatMap(userRepository::save)
                .then();
    }

    public Mono<Void> deleteUserById(@Nonnull String userId) {
        return userRepository.existsById(userId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)))
                .then(userRepository.deleteById(userId));
    }

    public Flux<User> findAllUsersByIds(@Nonnull Collection<String> userIds) {
        return userRepository.findAllById(userIds);
    }

    public Mono<Boolean> existsById(@Nonnull String userId) {
        return userRepository.existsById(userId);
    }
}
