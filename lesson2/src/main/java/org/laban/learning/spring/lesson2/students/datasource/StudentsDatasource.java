package org.laban.learning.spring.lesson2.students.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson2.events.StudentCreatedEvent;
import org.laban.learning.spring.lesson2.events.StudentDeletedEvent;
import org.laban.learning.spring.lesson2.exceptions.StudentNotFoundError;
import org.laban.learning.spring.lesson2.students.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StudentsDatasource {
    private static final String SPLITTER = ";";

    private final Map<Long, Student> students = new HashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;
    @Value("${app.init.file.enabled}")
    private Boolean enableInitFromFile;
    @Value("${app.init.file.name}")
    private String studentsFileName;


    @ConditionalOnProperty(value = "${app.init.file.enabled}", havingValue = "true")
    @EventListener(classes = ApplicationStartedEvent.class)
    public void initData() {
        if (enableInitFromFile) {
            log.info("Initialization from file started");
            read().forEach(this::save);
        } else {
            log.info("Initialization from file disabled");
        }
    }

    @Nonnull
    private List<Student> read() {
        var students = new ArrayList<Student>();
        var resource = new ClassPathResource(studentsFileName);
        try (var scanner = new Scanner(resource.getInputStream())) {
            while (scanner.hasNextLine()) {
                var record = scanner.nextLine();
                var student = parseStudent(record);
                if (student != null) {
                    students.add(student);
                } else {
                    log.error("Unrecognized student record: '{}'", record);
                }
            }
        } catch (IOException e) {
            log.error("Error during initialization from file", e);
        }
        return students;
    }

    @Nullable
    private Student parseStudent(@Nonnull String line) {
        var split = Arrays.stream(line.split(SPLITTER))
                .map(String::trim)
                .toList();
        if (split.size() != 3) {
            log.warn("Student file record must contains first name, last name and age divided by {}, " +
                    "but actual is {}", SPLITTER, line);
            return null;
        }
        var firstName = split.get(0);
        if (StringUtils.isBlank(firstName)) {
            log.warn("Student first name must not be emtpy");
            return null;
        }
        var lastName = split.get(1);
        if (StringUtils.isBlank(lastName)) {
            log.warn("Student last name must not be empty");
        }
        var age = parseAge(split.get(2));
        if (age == null) {
            log.warn("Student age must not be empty and must consist of numeric symbols, " +
                    "but actual is {}", split.get(2));
        }
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }

    @Nullable
    private Integer parseAge(@Nonnull String strAge) {
        try {
            return Integer.parseInt(strAge);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Nonnull
    public List<Student> getAllStudents() {
        return students.values().stream().toList();
    }

    public Student save(@Nonnull final Student student) {
        var id = StudentSequence.nextId();
        var savedStudent = student.toBuilder().id(id).build();
        students.put(savedStudent.getId(), savedStudent);
        applicationEventPublisher.publishEvent(new StudentCreatedEvent(this, savedStudent.getId()));
        return savedStudent;
    }

    public Student edit(@Nonnull final Long id, @Nonnull final Student student) {
        var currentStudent = students.get(id);
        if (currentStudent == null) {
            throw new StudentNotFoundError(id);
        }
        var savedStudent = student
                .toBuilder()
                .id(currentStudent.getId())
                .build();
        students.put(savedStudent.getId(), savedStudent);
        return savedStudent;
    }

    public Student delete(@Nonnull Long id) {
        var deleted = students.remove(id);
        if (deleted == null) {
            throw new StudentNotFoundError(id);
        }
        applicationEventPublisher.publishEvent(new StudentDeletedEvent(this, deleted.getId()));
        return deleted;
    }

}
