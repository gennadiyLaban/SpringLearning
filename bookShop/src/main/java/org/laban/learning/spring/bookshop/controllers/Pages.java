package org.laban.learning.spring.bookshop.controllers;

public class Pages {
    private Pages() {}

    public static String redirect(String path) {
        return "redirect:%s".formatted(path);
    }
}
