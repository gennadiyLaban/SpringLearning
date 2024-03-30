package org.laban.learning.spring.lesson5.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lesson5.exception.BookByNameAndAuthorNotFound;
import org.laban.learning.spring.lesson5.exception.BookNotFoundException;
import org.laban.learning.spring.lesson5.exception.CategoryNameNotFound;
import org.laban.learning.spring.lesson5.mapper.BookMapper;
import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.model.Category;
import org.laban.learning.spring.lesson5.properties.AppCacheProperties;
import org.laban.learning.spring.lesson5.repository.BookRepository;
import org.laban.learning.spring.lesson5.repository.CategoryRepository;
import org.laban.learning.spring.lesson5.web.dto.BookDTO;
import org.laban.learning.spring.lesson5.web.dto.BookListDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    public BookDTO findBookDtoById(@Nonnull Long id) {
        return findBookById(id)
                .map(bookMapper::bookToBookDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Book> findBookById(@Nonnull Long id) {
        return bookRepository.findById(id);
    }

    @Cacheable(value = AppCacheProperties.CacheNames.FIND_DTO_BY_NAME_AND_AUTHOR, key = "#name + #author")
    @Transactional(readOnly = true)
    public BookDTO findDtoByNameAndAuthor(@Nonnull String name, @Nonnull String author) {
        log.debug("request bookDTO by name={} and author={}", name, author);
        return findByNameAndAuthor(name, author)
                .map(bookMapper::bookToBookDTO)
                .orElseThrow(() -> new BookByNameAndAuthorNotFound(name, author));
    }

    @Transactional(readOnly = true)
    public Optional<Book> findByNameAndAuthor(@Nonnull String name, @Nonnull String author) {
        return bookRepository.findByNameAndAuthor(name, author);
    }

    @Cacheable(value = AppCacheProperties.CacheNames.FIND_ALL_DTO_BY_CATEGORY, key = "#categoryName")
    @Transactional(readOnly = true)
    public BookListDTO findAllDtoByCategory(@Nonnull String categoryName) {
        log.debug("request BookListDTO by categoryName={}", categoryName);
        return bookMapper.bookListToBookListDTO(findAllByCategory(categoryName));
    }

    @Transactional(readOnly = true)
    public List<Book> findAllByCategory(@Nonnull String categoryName) {
        var category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new CategoryNameNotFound(categoryName));
        return bookRepository.findBooksByCategory(category);
    }

    @CacheEvict(value = AppCacheProperties.CacheNames.FIND_ALL_DTO_BY_CATEGORY, key = "#upsertBook.category")
    @Transactional
    public Long createBookByDto(@Nonnull BookDTO upsertBook) {
        return createBook(bookMapper.bookDTOtoBook(
                upsertBook,
                getOrCreateCategoryByName(upsertBook.getCategory())
        ));
    }

    @Transactional
    public Long createBook(@Nonnull Book upsertBook) {
        return bookRepository.save(upsertBook).getId();
    }


    @Nonnull
    private Category getOrCreateCategoryByName(@Nonnull String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                .orElseGet(() -> categoryRepository.save(
                        Category.builder().name(categoryName).build()
                ));
    }
}
