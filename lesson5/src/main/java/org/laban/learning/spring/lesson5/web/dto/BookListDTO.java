package org.laban.learning.spring.lesson5.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class BookListDTO implements Serializable {
    private final List<BookDTO> books;
}
