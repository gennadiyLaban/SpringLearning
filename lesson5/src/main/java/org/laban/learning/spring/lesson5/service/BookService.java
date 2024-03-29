package org.laban.learning.spring.lesson5.service;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.properties.AppCacheProperties;
import org.laban.learning.spring.lesson5.repository.BookRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Cacheable(value = AppCacheProperties.CacheNames.FIND_ALL)
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

}
