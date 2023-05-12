package org.laban.learning.spring.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data @NoArgsConstructor
public class Book {
    private Long id;
    @Nullable private String author;
    @Nullable private String title;
    @Nullable private Integer size;
}
