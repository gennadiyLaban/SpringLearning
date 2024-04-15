package org.laban.learning.spring.lesson4.withprotection.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.laban.learning.spring.lesson4.withprotection.exception.UserNotFoundException;
import org.laban.learning.spring.lesson4.withprotection.mapper.UserMapper;
import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.laban.learning.spring.lesson4.withprotection.repository.UserRepository;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.utils.BeanUtils;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDTO getUserDTOById(@Nonnull Long id) {
        return userMapper.userToRestrictUserDTO(getUserById(id));
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
    public List<User> getAllByIds(@Nonnull List<Long> ids) {
        return findAllByIds(ids)
                .orElseThrow(() -> new UserNotFoundException(ids));
    }

    @Transactional(readOnly = true)
    public Optional<List<User>> findAllByIds(@Nonnull List<Long> ids) {
        return Optional.of(userRepository.findAllById(ids))
                .filter(users -> !users.isEmpty());
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
    public Long createUserByDTO(@Nonnull UserDTO request) {
        var upsertUser = userMapper.userDTOtoUser(request);
        var createdUser = userRepository.save(upsertUser);
        return createdUser.getId();
    }

    @Transactional
    public void updateUserByDTO(AppUserDetails userDetails, @Nonnull UserDTO userDTO) {
        var upsertUser = userMapper.userDTOtoUser(userDTO);
        var existedUser = userRepository.findById(upsertUser.getId())
                .orElseThrow(() -> new UserNotFoundException(upsertUser.getId()));

        var excludedFields = excludeFieldsFromCopy(userDetails, existedUser, userDetails.getRoles());
        BeanUtils.copyNonNullProperties(upsertUser, existedUser, excludedFields);
    }

    @NotNull
    private HashSet<String> excludeFieldsFromCopy(
            AppUserDetails userDetails,
            User existedUser,
            Set<RoleType> authUserRoles
    ) {
        var excludedFields = new HashSet<String>();
        if (!Objects.equals(userDetails.getId(), existedUser.getId())) {
            excludedFields.add(User.Fields.username);
            excludedFields.add(User.Fields.password);
        }
        if (!authUserRoles.contains(RoleType.ROLE_ADMIN)) {
            excludedFields.add(User.Fields.roles);
        }
        return excludedFields;
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
