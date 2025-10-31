package art.lapov.adapterdb;

import art.lapov.domain.exception.InvalidInputException;
import art.lapov.domain.exception.TaskNotFoundException;
import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.port.out.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Task getById(TaskId id) {
        if (id == null) {
            throw new InvalidInputException("Task id is required");
        }
        return taskJpaRepository.findById(id.getValue())
                .map(taskMapper::toDomain)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    @Override
    public List<Task> getAll() {
        return taskJpaRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .toList();
    }

    @Override
    public List<Task> getByUserId(String userId) {
        return taskJpaRepository.findAllByUserId(userId).stream()
                .map(taskMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TaskId id) {
        taskJpaRepository.deleteById(id.getValue());
    }
}
