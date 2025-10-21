package art.lapov.todohexagonal;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.User;
import art.lapov.domain.port.in.CreateTaskUseCase;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.FindTasksUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
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

    @Override
    public void run(String... args) {
        log.info("=== Hello from DataInitializer ===");

        log.info("=== Create users ===");
        User user1 = createUserUseCase.createUser("Claude", "Dubois", "claude@test.com");
        log.info("User crated : {}", user1);

        User user2 = createUserUseCase.createUser("john", "Dupont", "john@test.com");
        log.info("User created: {}", user2);

        User user3 = createUserUseCase.createUser("madame", "dupont", "madame@test.com");
        log.info("User created: {}", user3);

        log.info("=== List of all users ===");
        List<User> allUsers = findUsersUseCase.getAllUsers();
        allUsers.forEach(user -> log.info("User: {}", user));

        log.info("=== Total users: {} ===", allUsers.size());

        log.info("=== Create tasks ===");
        Task task1 = createTaskUseCase.createTask("Buy milk", "Buy milk for John", user1.getId());
        log.info("Task created: {}", task1);

        Task task2 = createTaskUseCase.createTask("Buy eggs", "Buy eggs for John", user1.getId());
        log.info("Task created: {}", task2);

        Task task3 = createTaskUseCase.createTask("Buy eggs", "Buy eggs for John", user2.getId());
        log.info("Task created: {}", task3);

        log.info("=== List of all tasks ===");
        List<Task> allTasks = findTasksUseCase.getAllTasks();
        allTasks.forEach(task -> log.info("Task: {}", task));

        log.info("=== End of DataInitializer ===");
    }
}