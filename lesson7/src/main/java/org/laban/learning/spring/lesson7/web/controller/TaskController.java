package org.laban.learning.spring.lesson7.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.service.TaskService;
import org.laban.learning.spring.lesson7.web.dto.TaskDTO;
import org.laban.learning.spring.lesson7.web.dto.TaskListDTO;
import org.laban.learning.spring.lesson7.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public Mono<ResponseEntity<TaskDTO>> findTaskById(@PathVariable String taskId) {
        return taskService.getTaskDTObyId(taskId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<TaskListDTO>> findAllTasks() {
        return taskService.findAllTasks()
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createTask(
            @Validated(ValidationGroup.Create.class)
            @RequestBody
            TaskDTO taskDTO
    ) {
        return taskService.createTask(taskDTO)
                .map(taskId -> ResponseEntity.created(URI.create("/api/v1/task?taskId=" + encode(taskId))).build());
    }

    @PutMapping("/{taskId}")
    public Mono<ResponseEntity<Void>> updateTask(
            @PathVariable
            String taskId,
            @Validated(ValidationGroup.Update.class)
            @RequestBody
            TaskDTO taskDTO
    ) {
        return taskService.updateTask(taskDTO)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{taskId}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String taskId) {
        return taskService.deleteTaskById(taskId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping("/{taskId}/observers/add")
    public Mono<ResponseEntity<Void>> addUserAsTaskObserver(
            @PathVariable String taskId,
            @RequestParam String userId
    ) {
        return taskService.addUserAsTaskObserver(taskId, userId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
