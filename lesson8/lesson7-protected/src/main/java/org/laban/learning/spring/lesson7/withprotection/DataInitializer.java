package org.laban.learning.spring.lesson7.withprotection;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.withprotection.model.Task;
import org.laban.learning.spring.lesson7.withprotection.model.TaskStatus;
import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.laban.learning.spring.lesson7.withprotection.repository.TaskRepository;
import org.laban.learning.spring.lesson7.withprotection.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final static String ID_USER_1 = "user-1";
    private final static String ID_USER_2 = "user-2";
    private final static String ID_USER_3 = "user-3";

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void initDefaultUser() {
        userRepository.deleteAll()
                .thenMany(userRepository.saveAll(createUsers()))
                .thenMany(taskRepository.deleteAll())
                .thenMany(taskRepository.saveAll(createTasks()))
                .blockLast();
    }

    private List<User> createUsers() {
        return List.of(
                User.builder().id(ID_USER_1).name("User1").email("user1@yandex.ru").build(),
                User.builder().id(ID_USER_2).name("User2").email("user2@mail.ru").build(),
                User.builder().id(ID_USER_3).name("User3").email("user3@gmail.com").build()
        );
    }

    private List<Task> createTasks() {
        var instant1 = Instant.now();
        var instant2 = instant1.plus(123, ChronoUnit.SECONDS);
        var instant3 = instant1.plus(123, ChronoUnit.SECONDS);
        return List.of(
                Task.builder().id("task-1").name("Task1").description("Description1")
                        .createdAt(instant1).updatedAt(instant1.plus(1, ChronoUnit.MINUTES))
                        .status(TaskStatus.DONE)
                        .authorId(ID_USER_1).assigneeId(ID_USER_2).observerIds(Set.of(ID_USER_3))
                        .build(),
                Task.builder().id("task-2").name("Task2").description("Description2")
                        .createdAt(instant2).updatedAt(instant2.plus(2, ChronoUnit.MINUTES))
                        .status(TaskStatus.IN_PROGRESS)
                        .authorId(ID_USER_2).assigneeId(ID_USER_3).observerIds(Set.of(ID_USER_1))
                        .build(),
                Task.builder().id("task-3").name("Task3").description("Description3")
                        .createdAt(instant3).updatedAt(instant3.plus(3, ChronoUnit.MINUTES))
                        .status(TaskStatus.TODO)
                        .authorId(ID_USER_3).assigneeId(ID_USER_1).observerIds(Set.of(ID_USER_2, ID_USER_3))
                        .build()
        );
    }

}
