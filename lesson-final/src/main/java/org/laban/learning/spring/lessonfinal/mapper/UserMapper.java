package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.web.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO entityToDTO(User user);
}
