package org.laban.learning.spring.lesson4.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.exception.UserNotFoundException;
import org.laban.learning.spring.lesson4.mapper.UserMapper;
import org.laban.learning.spring.lesson4.model.User;
import org.laban.learning.spring.lesson4.repository.UserRepository;
import org.laban.learning.spring.lesson4.utils.BeanUtils;
import org.laban.learning.spring.lesson4.web.dto.user.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.user.UserListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDTO findUserDTOById(@Nonnull Long id) {
        return findUserById(id)
                .map(userMapper::userToUserDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public UserListDTO findAllByDTO(@Nonnull Pageable pageable) {
        var page = findAll(pageable);
        return userMapper.userPageToUserListDTO(page);
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(@Nonnull Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Transactional
    public UserDTO createUserByDTO(@Nonnull UserDTO request) {
        var upsertUser = userMapper.userDTOtoUser(request);
        var createdUser = userRepository.save(upsertUser);
        return userMapper.userToUserDTO(createdUser);
    }

    @Transactional
    public void updateUserByDTO(@Nonnull UserDTO userDTO) {
        var upsertUser = userMapper.userDTOtoUser(userDTO);
        var existedUser = userRepository.findById(upsertUser.getId())
                .orElseThrow(() -> new UserNotFoundException(upsertUser.getId()));
        BeanUtils.copyNonNullProperties(upsertUser, existedUser);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
