package org.laban.learning.spring.bookshop.data.author;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Repository;

@Repository
public class AuthorsRepository {
    private final AuthorsDAO dao;

    public AuthorsRepository(AuthorsDAO dao) {
        this.dao = dao;
    }

    public List<Author> getAllAuthors() {
        return dao.retrieveAll();
    }

    public List<PageAuthorList> getPageAuthorLists() {
        var pageData = new LinkedHashMap<Character, PageAuthorList>();
        Function<Character, PageAuthorList> pageAuthorListCreator = (Character letter) -> PageAuthorList.builder()
                .letterUpperCase(Character.toUpperCase(letter))
                .letterLowerCase(Character.toLowerCase(letter))
                .authors(new ArrayList<>())
                .build();
        getAllAuthors()
                .stream()
                .sorted(Comparator.comparing(Author::getLastName))
                .forEach(author -> {
                    var authorList = pageData.computeIfAbsent(author.getLastName().charAt(0), pageAuthorListCreator);
                    authorList.getAuthors().add(author);
                });
        return pageData.values().stream().toList();
    }
}
