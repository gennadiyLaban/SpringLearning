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
import org.laban.learning.spring.lesson5.utils.BeanUtils;
import org.laban.learning.spring.lesson5.web.dto.BookDTO;
import org.laban.learning.spring.lesson5.web.dto.BookListDTO;
import org.springframework.cache.CacheManager;
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
    private final CacheManager cacheManager;

    @Transactional(readOnly = true)
    public BookDTO findBookDtoById(@Nonnull Long id) {
        return bookMapper.bookToBookDTO(getBookById(id));
    }

    @Transactional(readOnly = true)
    public Optional<Book> findBookById(@Nonnull Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Book getBookById(@Nonnull Long id) {
        return findBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
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

    @Transactional
    public void updateBookByDto(@Nonnull BookDTO upsertBook) {
        updateBook(bookMapper.bookDTOtoBook(
                upsertBook, getOrCreateCategoryByName(upsertBook.getCategory())
        ));
    }

    @Transactional
    public void updateBook(@Nonnull Book upsertBook) {
        var existedBook = getBookById(upsertBook.getId());
        var oldNameAndAuthorKey = existedBook.getName() + existedBook.getAuthor();
        var oldCategory = existedBook.getCategory();

        BeanUtils.copyNonNullProperties(upsertBook, existedBook);
        existedBook = bookRepository.save(existedBook);

        var newCategory = existedBook.getCategory();
        Optional.ofNullable(cacheManager.getCache(AppCacheProperties.CacheNames.FIND_ALL_DTO_BY_CATEGORY))
                .ifPresent(cache -> {
                    cache.evict(oldCategory.getName());
                    if (!oldCategory.equals(newCategory)) {
                        cache.evict(newCategory.getName());
                    }
                });
        Optional.ofNullable(cacheManager.getCache(AppCacheProperties.CacheNames.FIND_DTO_BY_NAME_AND_AUTHOR))
                .ifPresent(cache -> {
                    cache.evict(oldNameAndAuthorKey);
                });
    }


    @Nonnull
    private Category getOrCreateCategoryByName(@Nonnull String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                .orElseGet(() -> categoryRepository.save(
                        Category.builder().name(categoryName).build()
                ));
    }
}
