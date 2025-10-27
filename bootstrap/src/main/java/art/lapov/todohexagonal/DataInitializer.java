package art.lapov.todohexagonal;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.TaskStatus;
import art.lapov.domain.model.User;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
import art.lapov.domain.port.in.UpdateTaskUserCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TEMPORARY CLASS FOR TESTING PURPOSES ONLY
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CreateUserUseCase createUserUseCase;
    private final FindUsersUseCase findUsersUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final FindTasksUseCase findTasksUseCase;
    private final UpdateTaskUserCase updateTaskUserCase;

    @Override
    public void run(String... args) {
        log.info("=== Hello from DataInitializer ===");

        log.info("=== Creating sample users ===");
        User user1 = createUserUseCase.createUser("John", "Doe", "john.doe@example.com");
        log.info("User created: {}", user1);

        User user2 = createUserUseCase.createUser("Jane", "Smith", "jane.smith@example.com");
        log.info("User created: {}", user2);

        User user3 = createUserUseCase.createUser("Bob", "Johnson", "bob.johnson@example.com");
        log.info("User created: {}", user3);

        log.info("=== List of all users ===");
        List<User> allUsers = findUsersUseCase.getAllUsers();
        allUsers.forEach(user -> log.info("User: {}", user));

        log.info("=== Total users: {} ===", allUsers.size());

        log.info("=== Creating sample tasks ===");
        Task task1 = createTaskUseCase.createTask("Task 1", "First sample task", user1.getId());
        log.info("Task created: {} with status: {}", task1.getName(), task1.getStatus());

        Task task2 = createTaskUseCase.createTask("Task 2", "Second sample task", user2.getId());
        log.info("Task created: {} with status: {}", task2.getName(), task2.getStatus());
        task2 = updateTaskUserCase.updateTaskStatus(task2.getId(), TaskStatus.IN_PROGRESS);
        log.info("Task updated: {} with status: {}", task2.getName(), task2.getStatus());

        Task task3 = createTaskUseCase.createTask("Task 3", "Third sample task", user3.getId());
        log.info("Task created: {} with status: {}", task3.getName(), task3.getStatus());
        task3 = updateTaskUserCase.updateTaskStatus(task3.getId(), TaskStatus.IN_PROGRESS);
        task3 = updateTaskUserCase.updateTaskStatus(task3.getId(), TaskStatus.COMPLETED);
        log.info("Task updated: {} with status: {}", task3.getName(), task3.getStatus());

        log.info("=== List of all tasks ===");
        List<Task> allTasks = findTasksUseCase.getAllTasks();
        allTasks.forEach(task -> log.info("Task: {} - Status: {}", task.getName(), task.getStatus()));

        log.info("=== End of DataInitializer ===");
    }
}