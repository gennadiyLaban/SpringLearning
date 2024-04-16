package org.laban.learning.spring.lesson7.withprotection.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson7.withprotection.service.TaskService;
import org.laban.learning.spring.lesson7.withprotection.web.dto.TaskDTO;
import org.laban.learning.spring.lesson7.withprotection.web.dto.TaskListDTO;
import org.laban.learning.spring.lesson7.withprotection.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/{taskId}")
    public Mono<ResponseEntity<TaskDTO>> findTaskById(@PathVariable String taskId) {
        return taskService.getTaskDTObyId(taskId)
                .map(ResponseEntity::ok);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/list")
    public Mono<ResponseEntity<TaskListDTO>> findAllTasks() {
        return taskService.findAllTasks()
                .map(ResponseEntity::ok);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping
    public Mono<ResponseEntity<Void>> createTask(
            @Validated(ValidationGroup.Create.class)
            @RequestBody
            TaskDTO taskDTO,
            @AuthenticationPrincipal AppUserDetails principal
            ) {
        return taskService.createTask(taskDTO, principal)
                .map(taskId -> ResponseEntity.created(URI.create("/api/v1/task?taskId=" + encode(taskId))).build());
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
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

    @PreAuthorize("hasAnyRole('MANAGER')")
    @DeleteMapping("/{taskId}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String taskId) {
        return taskService.deleteTaskById(taskId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
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
