package org.laban.learning.spring.lesson7.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson7.service.TaskService;
import org.laban.learning.spring.lesson7.web.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public Mono<ResponseEntity<TaskDTO>> findTaskById(@RequestParam String taskId) {
        return taskService.findTaskDTObyId(taskId)
                .map(ResponseEntity::ok);
    }

}
