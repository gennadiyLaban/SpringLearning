package org.laban.learning.spring.lesson7.mapper;

import org.laban.learning.spring.lesson7.model.User;
import org.laban.learning.spring.lesson7.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO userToUserDTO(User user);

}
