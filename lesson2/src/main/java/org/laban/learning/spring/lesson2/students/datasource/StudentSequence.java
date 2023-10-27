package org.laban.learning.spring.lesson2.students.datasource;

public class StudentSequence {
    private static long ID = 1;

    private StudentSequence() {

    }

    public static long nextId() {
        return ID++;
    }

}
