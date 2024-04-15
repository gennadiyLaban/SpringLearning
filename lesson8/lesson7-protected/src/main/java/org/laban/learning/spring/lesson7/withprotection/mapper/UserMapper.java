package org.laban.learning.spring.lesson7.withprotection.mapper;

import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserDTO;
import org.laban.learning.spring.lesson7.withprotection.web.dto.UserListDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO userToUserDTO(User user);

    default UserListDTO userListToUserListDTO(List<User> users) {
        return UserListDTO.builder()
                .users(users.stream().map(this::userToUserDTO).toList())
                .build();
    }

    User userDTOtoUser(UserDTO userDTO);

    default Set<UserDTO> userSetToUserDTOset(Set<User> users) {
        return users.stream()
                .map(this::userToUserDTO)
                .collect(Collectors.toSet());
    }
}
