package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserListDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(UserMapperDelegate.class)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO entityToDTO(User user);

    default UserListDTO entityListToDtoList(List<User> users) {
        return UserListDTO.builder()
                .users(users.stream().map(this::entityToDTO).toList())
                .build();
    }

    @Mapping(target = "password", ignore = true)
    User dtoToEntity(UserDTO upsertUserDTO);
}
