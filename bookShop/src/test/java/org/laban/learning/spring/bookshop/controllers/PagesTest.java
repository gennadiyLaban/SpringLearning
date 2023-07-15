package org.laban.learning.spring.bookshop.controllers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PagesTest {

    @Test
    public void testDeclaredPages() throws NoSuchFieldException {
        for (Pages value : Pages.values()) {
            var controller = getAssociatedController(value);
            assertTrue(controller.isPresent());
            assertTrue(getRequestMethodsFor(controller.get()).contains(value.baseUrl));
        }
    }

    private Optional<Class<?>> getAssociatedController(Pages page) throws NoSuchFieldException {
        return Optional.of(page.getClass().getField(page.name()))
                .map(pagesClass -> pagesClass.getAnnotation(Pages.ForController.class))
                .map(Pages.ForController::value);
    }

    private Set<String> getRequestMethodsFor(Class<?> clazz) {
        var rootRequestMapping = getRootRequestMappingsFor(clazz);
        var methods = clazz.getMethods();
        var methodsRequestMapping = new HashSet<String>();
        for (var method : methods) {
            methodsRequestMapping.addAll(getRequestMappingsFor(method));
        }
        return rootRequestMapping.stream()
                .flatMap(rootPath -> {
                    return methodsRequestMapping.stream()
                            .map(methodPath -> rootPath + methodPath);
                })
                .collect(Collectors.toSet());
    }

    private Set<String> getRootRequestMappingsFor(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(RequestMapping.class))
                .map(annotation -> {
                    return Stream.concat(Arrays.stream(annotation.path()), Arrays.stream(annotation.value()))
                            .collect(Collectors.<String>toSet());
                })
                .orElseGet(() -> {
                    var set = new HashSet<String>();
                    set.add("");
                    return set;
                });
    }

    private Set<String> getRequestMappingsFor(Method target) {
        return Arrays.stream(target.getAnnotations())
                .filter(this::hasRequestMappingAnnotation)
                .flatMap((Annotation annotation) -> {
                    if (annotation instanceof RequestMapping castedAnnotation) {
                        return Stream.concat(
                                Arrays.stream(castedAnnotation.path()),
                                Arrays.stream(castedAnnotation.value())
                        );
                    }

                    if (annotation instanceof GetMapping castedAnnotation) {
                        return Stream.concat(
                                Arrays.stream(castedAnnotation.path()),
                                Arrays.stream(castedAnnotation.value())
                        );
                    }

                    if (annotation instanceof PostMapping castedAnnotation) {
                        return Stream.concat(
                                Arrays.stream(castedAnnotation.path()),
                                Arrays.stream(castedAnnotation.value())
                        );
                    }

                    return Stream.empty();
                })
                .collect(Collectors.toSet());
    }

    private boolean hasRequestMappingAnnotation(Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getAnnotations())
                .anyMatch(ann -> ann instanceof RequestMapping);
    }



//    private boolean controllerContains

}
