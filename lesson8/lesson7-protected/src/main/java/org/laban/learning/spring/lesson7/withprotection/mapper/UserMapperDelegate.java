package org.laban.learning.spring.lesson7.withprotection.mapper;

import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDelegate implements UserMapper {
    @Lazy
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userDTOtoUser(UserDTO userDTO) {
        return delegate.userDTOtoUser(userDTO)
                .toBuilder()
                .password(userDTO.getPassword() == null
                        ? null
                        : passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }
}
