package org.laban.learning.spring.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor
public class BookIdToRemove {
    @NotNull
    private Integer id;
}
