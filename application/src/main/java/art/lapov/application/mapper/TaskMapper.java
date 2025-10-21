package art.lapov.application.mapper;

import art.lapov.application.dto.TaskResponse;
import art.lapov.domain.model.Task;

public class TaskMapper {
    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId().toString())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .ownerId(task.getUserId().toString())
                .build();
    }

}
