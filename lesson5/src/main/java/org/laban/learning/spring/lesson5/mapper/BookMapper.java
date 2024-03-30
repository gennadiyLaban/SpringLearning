package org.laban.learning.spring.lesson5.mapper;

import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.model.Category;
import org.laban.learning.spring.lesson5.web.dto.BookDTO;
import org.laban.learning.spring.lesson5.web.dto.BookListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    @Mapping(target = "category", source = "category.name")
    BookDTO bookToBookDTO(Book book);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "id", source = "bookDTO.id")
    @Mapping(target = "name", source = "bookDTO.name")
    Book bookDTOtoBook(BookDTO bookDTO, Category category);

    default BookListDTO bookListToBookListDTO(List<Book> books) {
        return new BookListDTO(bookListToBookDtoList(books));
    }

    default List<BookDTO> bookListToBookDtoList(List<Book> books) {
        return books.stream().map(this::bookToBookDTO).toList();
    }
}
