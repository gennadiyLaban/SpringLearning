package org.laban.learning.spring.bookshop.services.author;

import java.util.List;

import org.laban.learning.spring.bookshop.data.author.AuthorSection;
import org.laban.learning.spring.bookshop.data.author.AuthorsRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorsService {
    private final AuthorsRepository repository;


    public AuthorsService(AuthorsRepository repository) {
        this.repository = repository;
    }

    public List<AuthorSection> getAuthorsPageData() {
        return repository.getAuthorsSections();
    }
}
