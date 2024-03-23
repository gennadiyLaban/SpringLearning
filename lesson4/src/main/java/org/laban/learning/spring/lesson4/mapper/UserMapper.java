package org.laban.learning.spring.lesson4.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.model.User;
import org.laban.learning.spring.lesson4.web.dto.user.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.user.UserListDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

    User userIdToUser(@Nullable Long userId);

    List<User> userIdsToUserList(@Nullable List<Long> userIds);

    default UserListDTO userPageToUserListDTO(Page<User> page) {
        return UserListDTO.builder()
                .users(page.getContent().stream()
                        .map(this::userToUserDTO)
                        .toList())
                .page(page.getNumber())
                .pageSize(page.getSize())
                .pageCount(page.getTotalPages())
                .build();
    }
}
