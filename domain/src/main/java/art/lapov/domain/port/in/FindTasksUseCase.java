package art.lapov.domain.port.in;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.UserId;

import java.util.List;

public interface FindTasksUseCase {
    Task getTaskById(TaskId id);
    List<Task> getTasksByUserId(UserId userId);
    List<Task> getAllTasks();
}
