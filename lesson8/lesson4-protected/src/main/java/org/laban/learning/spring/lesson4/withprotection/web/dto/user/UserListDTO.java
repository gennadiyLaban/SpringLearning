package org.laban.learning.spring.lesson4.withprotection.web.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;


@Data
@Builder
public class UserListDTO {
    @Singular
    private List<UserDTO> users;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
}
