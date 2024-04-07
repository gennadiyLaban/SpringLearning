package org.laban.learning.spring.lesson7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class TaskListDTO {
    private final List<TaskDTO> tasks;
}