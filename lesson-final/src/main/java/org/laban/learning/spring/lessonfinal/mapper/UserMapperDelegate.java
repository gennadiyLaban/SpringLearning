package org.laban.learning.spring.lessonfinal.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDelegate implements UserMapper {
    @Qualifier("delegate")
    @Autowired
    private UserMapper userMapperDelegate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User dtoToEntity(UserDTO upsertUserDTO) {
        var user = userMapperDelegate.dtoToEntity(upsertUserDTO);
        if (user == null) {
            return null;
        }

        return user.toBuilder()
                .password(encodePassword(upsertUserDTO.getPassword()))
                .build();
    }

    @Nullable
    private String encodePassword(@Nullable String password) {
        if (password == null) {
            return null;
        }

        return passwordEncoder.encode(password);
    }
}
