package art.lapov.domain.port.out;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> getById(TaskId id);
    List<Task> getAll();
    List<Task> getByUserId(String userId);
    void delete(TaskId id);
}
