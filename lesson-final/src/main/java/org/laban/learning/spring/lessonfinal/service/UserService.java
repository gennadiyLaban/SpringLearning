package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.UserNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.UserMapper;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.repository.UserRepository;
import org.laban.learning.spring.lessonfinal.utils.BeanUtils;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDTO getUserDtoById(@Nonnull Long id) {
        return userMapper.entityToDTO(getUserById(id));
    }

    @Transactional(readOnly = true)
    public User getUserById(@Nonnull Long id) {
        return findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(@Nonnull String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public UserListDTO findAllUserDTOs() {
        return userMapper.entityListToDtoList(findAllUsers());
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Long createUser(@Nonnull UserDTO upsertUserDTO) {
        return createUser(userMapper.dtoToEntity(upsertUserDTO));
    }

    @Transactional
    public Long createUser(@Nonnull User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    public void updateUser(@Nonnull UserDTO upsertUserDTO) {
        updateUser(userMapper.dtoToEntity(upsertUserDTO));
    }

    @Transactional
    public void updateUser(@Nonnull User upsertUser) {
        var existedUser = getUserById(upsertUser.getId());
        BeanUtils.copyNonNullProperties(upsertUser, existedUser);
    }
}
