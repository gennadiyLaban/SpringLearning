package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasePageContext {
    public static final Pages MAIN = Pages.MAIN;
    public static final Pages RECENT = Pages.RECENT;
    public static final Pages POPULAR = Pages.POPULAR;
    public static final Pages POSTPONED = Pages.POSTPONED;
    public static final Pages AUTHORS = Pages.AUTHORS;
    public static final Pages GENRES = Pages.GENRES;
    public static final Pages CART = Pages.CART;
    public static final Pages SIGNIN = Pages.SIGNIN;
    public static final Pages SEARCH = Pages.SEARCH;
    public static final Pages DOCUMENTS = Pages.DOCUMENTS;
    public static final Pages ABOUT = Pages.ABOUT;
    public static final Pages FAQ = Pages.FAQ;
    public static final Pages CONTACTS = Pages.CONTACTS;
}
