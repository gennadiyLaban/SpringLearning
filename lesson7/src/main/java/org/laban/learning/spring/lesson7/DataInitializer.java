package org.laban.learning.spring.lesson7;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.model.User;
import org.laban.learning.spring.lesson7.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final UserRepository userRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void initDefaultUser() {
        userRepository.deleteAll()
                .thenMany(userRepository.saveAll(createUsers()))
                .blockLast();
    }

    private List<User> createUsers() {
        return List.of(
                User.builder().id("user-1").name("User1").email("user1@yandex.ru").build(),
                User.builder().id("user-2").name("User2").email("user2@mail.ru").build(),
                User.builder().id("user-3").name("User3").email("user3@gmail.com").build()
        );
    }

}
