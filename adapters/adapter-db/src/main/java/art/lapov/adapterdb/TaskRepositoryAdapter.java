package art.lapov.adapterdb;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.port.out.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    TaskJpaRepository taskJpaRepository;
    TaskMapper taskMapper;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository, TaskMapper taskMapper) {
        this.taskJpaRepository = taskJpaRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = taskMapper.toTaskEntity(task);
        TaskEntity savedTask = taskJpaRepository.save(taskEntity);
        return taskMapper.toDomain(savedTask);
    }

    @Override
    public Optional<Task> getById(TaskId id) {
        return Optional.empty();
    }

    @Override
    public List<Task> getAll() {
        return taskJpaRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .toList();
    }

    @Override
    public List<Task> getByUserId(String userId) {
        return List.of();
    }

    @Override
    public void delete(TaskId id) {

    }
}
