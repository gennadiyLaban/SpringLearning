package org.laban.learning.spring.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class BookIdToRemove {
    @NotNull
    private Integer id;
}
