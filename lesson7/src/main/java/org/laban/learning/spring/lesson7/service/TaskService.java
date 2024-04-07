package org.laban.learning.spring.lesson7.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson7.exception.TaskNotFoundException;
import org.laban.learning.spring.lesson7.mapper.TaskMapper;
import org.laban.learning.spring.lesson7.model.Task;
import org.laban.learning.spring.lesson7.model.User;
import org.laban.learning.spring.lesson7.repository.TaskRepository;
import org.laban.learning.spring.lesson7.utils.BeanUtils;
import org.laban.learning.spring.lesson7.web.dto.TaskDTO;
import org.laban.learning.spring.lesson7.web.dto.TaskListDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final UserService userService;

    public Mono<TaskDTO> getTaskDTObyId(@Nonnull String taskId) {
        return getTaskById(taskId)
                .map(taskMapper::taskToTaskDTO);
    }

    public Mono<Task> getTaskById(@Nonnull String taskId) {
        return findTaskById(taskId)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(taskId)));
    }

    public Mono<Task> findTaskById(@Nonnull String taskId) {
        return taskRepository.findById(taskId)
                .flatMap(this::insertUsersForTask);
    }

    public Mono<TaskListDTO> findAllTasks() {
        return taskRepository
                .findAll()
                .flatMap(this::insertUsersForTask)
                .collectList()
                .map(taskMapper::taskListToTaskListDTO);
    }

    private Mono<Task> insertUsersForTask(@Nonnull Task task) {
        return Mono.zip(insertAuthor(task), insertAssignee(task), insertObservers(task))
                .thenReturn(task);
    }

    private Mono<Task> insertAuthor(@Nonnull Task task) {
        return getExistedOrEmptyUser(task.getAuthorId())
                .doOnSuccess(task::setAuthor)
                .thenReturn(task);
    }

    private Mono<Task> insertAssignee(@Nonnull Task task) {
        return getExistedOrEmptyUser(task.getAssigneeId())
                .doOnSuccess(task::setAssignee)
                .thenReturn(task);
    }

    private Mono<User> getExistedOrEmptyUser(@Nonnull String userId) {
        if (StringUtils.isBlank(userId)) {
            return Mono.empty();
        }

        return userService.findUserById(userId)
                .defaultIfEmpty(emptyUser(userId));
    }

    private Mono<Task> insertObservers(@Nonnull Task task) {
        var observersIds = task.getObserverIds();
        if (CollectionUtils.isEmpty(observersIds)) {
            task.setObservers(Collections.emptySet());
            return Mono.just(task);
        }

        return userService.findAllUsersByIds(observersIds)
                .collect(Collectors.toMap(User::getId, user -> user))
                .map(users -> {
                    if (users.size() == observersIds.size()) {
                        return new HashSet<>(users.values());
                    }

                    return observersIds.stream().map(userId -> {
                        var user = users.get(userId);
                        return user == null ? emptyUser(userId) : user;
                    }).collect(Collectors.toSet());
                })
                .doOnSuccess(task::setObservers)
                .thenReturn(task);
    }

    private User emptyUser(@Nonnull String userId) {
        return User.builder().id(userId).build();
    }

    public Mono<String> createTask(@Nonnull TaskDTO taskDTO) {
        return Mono.just(taskDTO)
                .map(taskMapper::taskDTOtoTask)
                .flatMap(this::validateUsersExist)
                .map(task -> {
                    var instant = Instant.now();
                    return task.withCreatedAt(instant).withUpdatedAt(instant);
                })
                .flatMap(taskRepository::save)
                .map(Task::getId);
    }

    private Mono<Task> validateUsersExist(@Nonnull Task task) {
        var userIds = new HashSet<String>();
        if (!CollectionUtils.isEmpty(task.getObserverIds())) {
            userIds.addAll(task.getObserverIds());
        }
        if (StringUtils.isNotBlank(task.getAuthorId())) {
            userIds.add(task.getAuthorId());
        }
        if (StringUtils.isNotBlank(task.getAssigneeId())) {
            userIds.add(task.getAssigneeId());
        }

        return Flux.fromIterable(userIds)
                .flatMap(userId -> userService.existsById(userId)
                        .filter(exists -> !exists)
                        .flatMap(notExists -> Mono.error(new TaskNotFoundException(userId))))
                .then(Mono.just(task));
    }

    public Mono<String> updateTask(@Nonnull TaskDTO taskDTO) {
        return Mono.just(taskDTO)
                .map(taskMapper::taskDTOtoTask)
                .flatMap(upsertTask -> Mono.zip(
                        getTaskById(upsertTask.getId()),
                        Mono.just(upsertTask)
                ))
                .map(tasks -> {
                    var existedTask = tasks.getT1();
                    var upsertTask = tasks.getT2();
                    BeanUtils.copyNonNullProperties(upsertTask, existedTask);
                    return existedTask;
                })
                .flatMap(this::validateUsersExist)
                .flatMap(taskRepository::save)
                .map(Task::getId);
    }

    public Mono<Void> deleteTaskById(@Nonnull String taskId) {
        return taskRepository.existsById(taskId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(taskId)))
                .then(taskRepository.deleteById(taskId));
    }
}
