package art.lapov.domain.port.in;

import art.lapov.domain.model.TaskId;

public interface DeleteTaskUseCase {
    void deleteTask(TaskId id);
}
