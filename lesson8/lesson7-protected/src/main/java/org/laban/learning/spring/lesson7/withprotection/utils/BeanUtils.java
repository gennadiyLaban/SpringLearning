package org.laban.learning.spring.lesson7.withprotection.utils;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class BeanUtils {
    private BeanUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    @SneakyThrows
    public static void copyNonNullProperties(Object source, Object destination) {
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);

            if (value != null) {
                field.set(destination, value);
            }
        }
    }
}
