package art.lapov.adapterweb;

import art.lapov.application.dto.TaskResponse;
import art.lapov.application.mapper.TaskMapper;
import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.DeleteTaskUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.UpdateTaskUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if (!task.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskMapper.toTaskResponse(task.get()));
    }

}
