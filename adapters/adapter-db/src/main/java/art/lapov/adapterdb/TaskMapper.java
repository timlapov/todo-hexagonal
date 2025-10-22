package art.lapov.adapterdb;

import art.lapov.domain.model.Task;

import java.util.Optional;

public class TaskMapper {

        public TaskEntity toTaskEntity(Task task) {
            return new TaskEntity(
            task.getId().toString(),
            task.getName(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getUserId().toString()
        );
    }

    public Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getUserId()
        );
    }

    public Optional<Task> toDomain(Optional<TaskEntity> entity) {
        return entity.map(e -> new Task(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getUserId()
        ));
    }

}
