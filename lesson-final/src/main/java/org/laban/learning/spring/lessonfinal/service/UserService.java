package org.laban.learning.spring.lessonfinal.service;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.UserNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.UserMapper;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.repository.UserRepository;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDTO getUserDtoById(Long id) {
        return userMapper.entityToDTO(getUserById(id));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

}
