package org.laban.learning.spring.lesson7.mapper;

import org.laban.learning.spring.lesson7.model.Task;
import org.laban.learning.spring.lesson7.web.dto.TaskDTO;
import org.laban.learning.spring.lesson7.web.dto.TaskListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = UserMapper.class
)
public interface TaskMapper {
    TaskDTO taskToTaskDTO(Task task);

    Task taskDTOtoTask(TaskDTO taskDTO);

    default TaskListDTO taskListToTaskListDTO(List<Task> tasks) {
        return TaskListDTO.builder()
                .tasks(tasks.stream().map(this::taskToTaskDTO).toList())
                .build();
    }
}
