package art.lapov.domain.port.in;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.TaskStatus;

public interface UpdateTaskUserCase {
    Task updateTaskDetails(TaskId id, String name, String description);
    Task updateTaskStatus(TaskId id, TaskStatus status);
}
