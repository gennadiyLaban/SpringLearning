package org.laban.learning.spring.lesson4.withprotection.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.laban.learning.spring.lesson4.withprotection.service.UserService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

public abstract class UserMapperDelegate implements UserMapper {
    @Lazy
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userDTOtoUser(UserDTO userDTO) {
        var user = delegate.userDTOtoUser(userDTO);
        var password = userDTO.getPassword();
        return user.toBuilder()
                .password(password == null ? null : passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }

    @Override
    public User userIdToUser(@Nullable Long userId) {
        if (userId == null) {
            return null;
        }

        return userService.getUserById(userId);
    }

    @Override
    public List<User> userIdsToUserList(@Nullable List<Long> userIds) {
        if (userIds == null) {
            return null;
        }
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        return userService.getAllByIds(userIds);
    }
}
