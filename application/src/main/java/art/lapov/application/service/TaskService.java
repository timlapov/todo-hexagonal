package art.lapov.application.service;

import art.lapov.application.mapper.TaskMapper;
import art.lapov.domain.model.*;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.DeleteTaskUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.UpdateTaskUserCase;
import art.lapov.domain.port.out.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Task task = new Task(name, description, userId.getValue());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(TaskId id) {
        checkIdIsNotNull(id);
        checkTaskExists(id);
        taskRepository.delete(id);
    }

    @Override
    public Optional<Task> getTaskById(TaskId id) {
        checkIdIsNotNull(id);
        checkTaskExists(id);
        return taskRepository.getById(id);
    }

    @Override
    public List<Task> getTasksByUserId(UserId userId) {
        if (userId == null || userId.getValue().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
        }
        return taskRepository.getByUserId(userId.getValue());
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAll();
    }

    @Override
    public Task updateTaskDetails(TaskId id, String name, String description) {
        checkIdIsNotNull(id);
        checkTaskExists(id);
        Task task = taskRepository.getById(id).orElseThrow();
        if (name != null && description != null) {
            task.update(name, description);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task name and description are required");
    }

    @Override
    public Task updateTaskStatus(TaskId id, TaskStatus status) {
        checkIdIsNotNull(id);
        checkTaskExists(id);
        Task task = taskRepository.getById(id).orElseThrow();
        switch (status) {
            case COMPLETED -> task.complete();
            case CANCELLED -> task.cancel();
            case IN_PROGRESS -> task.inProgress();
            default -> throw new IllegalArgumentException("Invalid task status");
        }
        return taskRepository.save(task);
    }

    private void validateCreateRequest(String name, UserId userId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
    }

    private void checkTaskExists(TaskId id) {
        checkIdIsNotNull(id);
        Optional<Task> task = taskRepository.getById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " not found");
        }
    }

    private static void checkIdIsNotNull(TaskId id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id is required");
        }
    }

}
