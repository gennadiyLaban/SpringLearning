package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO entityToDTO(User user);

    default UserListDTO entityListToDtoList(List<User> users) {
        return UserListDTO.builder()
                .users(users.stream().map(this::entityToDTO).toList())
                .build();
    }

    User dtoToEntity(UserDTO upsertUserDTO);
}
