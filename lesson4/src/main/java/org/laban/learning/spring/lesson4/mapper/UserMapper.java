package org.laban.learning.spring.lesson4.mapper;

import org.laban.learning.spring.lesson4.model.User;
import org.laban.learning.spring.lesson4.web.dto.UserDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListDTO;
import org.laban.learning.spring.lesson4.web.dto.UserListRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

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

    default Pageable userListRequestToPageable(UserListRequest request) {
        return Pageable
                .ofSize(request.getSize())
                .withPage(request.getPage());
    }
}
