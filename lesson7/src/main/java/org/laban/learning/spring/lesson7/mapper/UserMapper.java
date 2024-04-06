package org.laban.learning.spring.lesson7.mapper;

import org.laban.learning.spring.lesson7.model.User;
import org.laban.learning.spring.lesson7.web.dto.UserDTO;
import org.laban.learning.spring.lesson7.web.dto.UserListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
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
