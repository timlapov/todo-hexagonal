package art.lapov.adapterdb;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.port.out.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskRepositoryAdapter implements TaskRepository {



    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Optional<Task> getById(TaskId id) {
        return Optional.empty();
    }

    @Override
    public List<Task> getAll() {
        return List.of();
    }

    @Override
    public List<Task> getByUserId(String userId) {
        return List.of();
    }

    @Override
    public void delete(TaskId id) {

    }
}
