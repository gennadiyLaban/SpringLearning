package org.laban.learning.spring.lesson4.withprotection.utils;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

public class BeanUtils {
    private BeanUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static void copyNonNullProperties(Object source, Object destination) {
        copyNonNullProperties(source, destination, Collections.emptySet());
    }

    @SneakyThrows
    public static void copyNonNullProperties(Object source, Object destination, Set<String> excludeFields) {
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);

            if (value != null && !excludeFields.contains(field.getName())) {
                field.set(destination, value);
            }
        }
    }
}
