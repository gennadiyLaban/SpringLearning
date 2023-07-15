package org.laban.learning.spring.bookshop.controllers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public enum Pages {
    @ForController(MainPageController.class)
    MAIN("/"),
    @ForController(RecentPageController.class)
    RECENT("/books/recent"),
    @ForController(PopularPageController.class)
    POPULAR("/books/popular"),
    @ForController(PostponedPageController.class)
    POSTPONED("/postponed"),
    @ForController(AuthorsPageController.class)
    AUTHORS("/authors"),
    @ForController(GenresPageController.class)
    GENRES("/genres"),
    @ForController(CartPageController.class)
    CART("/cart"),
    @ForController(SigninPageController.class)
    SIGNIN("/signin"),
    @ForController(SearchPageController.class)
    SEARCH("/search"),
    @ForController(DocumentsPageController.class)
    DOCUMENTS("/documents"),
    @ForController(AboutPageController.class)
    ABOUT("/about"),
    @ForController(FaqPageController.class)
    FAQ("/faq"),
    @ForController(ContactsPageController.class)
    CONTACTS("/contacts");


    Pages(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public final String baseUrl;

    @Retention(RetentionPolicy.RUNTIME)
    @interface ForController {
        Class<?> value();
    }
}
