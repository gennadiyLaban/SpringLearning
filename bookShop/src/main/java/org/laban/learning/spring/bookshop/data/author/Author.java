package org.laban.learning.spring.bookshop.data.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    private Integer id;
    private String firstName;
    private String lastName;
}
