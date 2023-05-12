package org.laban.learning.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.StreamSupport;

public class MainLocal {
    public static void main(String[] args) {
        var list = new ArrayList<String>(8);
        list.add("some1");
        list.add("some2");
        list.add("some3");
        list.add("some4");
        list.add("some5");

        final var builder = new StringBuilder();
        System.out.println(
                list.stream().collect(
                        () -> builder,
                        StringBuilder::append,
                        (builder1, builder2) -> {}
                )
        );
    }
}
