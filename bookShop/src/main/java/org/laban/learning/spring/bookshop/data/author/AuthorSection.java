package org.laban.learning.spring.bookshop.data.author;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthorSection {
    private Character sectionLetter;
    private List<Author> authors;
}
