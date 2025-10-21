package art.lapov.application.service;

import art.lapov.application.dto.CreateTaskRequest;
import art.lapov.application.mapper.TaskMapper;
import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.TaskStatus;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.DeleteTaskUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.UpdateTaskUserCase;
import art.lapov.domain.port.out.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService implements CreateTaskUseCase, FindTasksUseCase, UpdateTaskUserCase, DeleteTaskUseCase {

    public final TaskRepository taskRepository;
    public final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task createTask(String name, String description, UserId userId) {
        validateCreateRequest(name, userId);
        Task task = new Task(name, description, userId);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(TaskId id) {

    }

    @Override
    public Task getTaskById(TaskId id) {
        if (id != null) {
            return taskRepository.getById(id).orElse(null);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUserId(UserId userId) {
        return List.of();
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAll();
    }

    @Override
    public Task updateTaskDetails(TaskId id, String name, String description) {
        return null;
    }

    @Override
    public Task updateTaskStatus(TaskId id, TaskStatus status) {
        return null;
    }

    private void validateCreateRequest(String name, UserId userId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
    }

}
