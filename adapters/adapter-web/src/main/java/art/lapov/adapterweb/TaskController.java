package art.lapov.adapterweb;

import art.lapov.application.dto.CreateTaskRequest;
import art.lapov.application.dto.TaskResponse;
import art.lapov.application.mapper.TaskMapper;
import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.DeleteTaskUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.UpdateTaskUserCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final FindTasksUseCase findTasksUseCase;
    private final UpdateTaskUserCase updateTaskUserCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll() {
        List<TaskResponse> taskResponses = findTasksUseCase.getAllTasks().stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        if (taskResponses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getOne(@PathVariable String id) {
        Optional<Task> task = findTasksUseCase.getTaskById(new TaskId(id));
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskMapper.toTaskResponse(task.get()));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid CreateTaskRequest request) {
        Task task = createTaskUseCase.createTask(request.getName(), request.getDescription(), new UserId(request.getUserId()));
        return ResponseEntity.ok(taskMapper.toTaskResponse(task));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteTaskUseCase.deleteTask(new TaskId(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getByUser(@PathVariable String userId) {
        List<Task> tasks = findTasksUseCase.getTasksByUserId(new UserId(userId));
        return ResponseEntity.ok(tasks.stream().map(taskMapper::toTaskResponse).toList());
    }

}
