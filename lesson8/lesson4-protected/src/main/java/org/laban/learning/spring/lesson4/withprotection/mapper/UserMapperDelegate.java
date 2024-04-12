package org.laban.learning.spring.lesson4.withprotection.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.laban.learning.spring.lesson4.withprotection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;
import java.util.List;

public abstract class UserMapperDelegate implements UserMapper {
    @Lazy
    @Autowired
    private UserService userService;

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
