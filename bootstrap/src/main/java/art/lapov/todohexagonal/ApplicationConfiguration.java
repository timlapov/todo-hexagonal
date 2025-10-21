package art.lapov.todohexagonal;

import art.lapov.adapterdb.TaskJpaRepository;
import art.lapov.adapterdb.TaskRepositoryAdapter;
import art.lapov.adapterdb.UserJpaRepository;
import art.lapov.adapterdb.UserRepositoryAdapter;
import art.lapov.application.service.TaskService;
import art.lapov.application.service.UserService;
import art.lapov.domain.port.out.TaskRepository;
import art.lapov.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring bean configuration for hexagonal architecture
 * Here we connect all layers: domain, application, adapters
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * Mapper for application layer (domain -> DTO)
     */
    @Bean
    public art.lapov.application.mapper.UserMapper applicationUserMapper() {
        return new art.lapov.application.mapper.UserMapper();
    }

    /**
     * Mapper for adapter-db (domain <-> entity)
     */
    @Bean
    public art.lapov.adapterdb.UserMapper adapterUserMapper() {
        return new art.lapov.adapterdb.UserMapper();
    }

    /**
     * Adapter for DB (port UserRepository)
     */
    @Bean
    public UserRepository userRepository(
            UserJpaRepository userJpaRepository,
            art.lapov.adapterdb.UserMapper userMapper) {
        return new UserRepositoryAdapter(userJpaRepository, userMapper);
    }

    /**
     * Use cases realisation
     */
    @Bean
    public UserService userService(
            UserRepository userRepository,
            art.lapov.application.mapper.UserMapper userMapper) {
        return new UserService(userRepository, userMapper);
    }

    /**
     * Mapper for application layer (domain -> DTO)
     */
    @Bean
    public art.lapov.application.mapper.TaskMapper applicationTaskMapper() {
        return new art.lapov.application.mapper.TaskMapper();
    }

    /**
     * Mapper for adapter-db (domain <-> entity)
     */
    @Bean
    public art.lapov.adapterdb.TaskMapper adapterTaskMapper() {
        return new art.lapov.adapterdb.TaskMapper();
    }

    /**
     * Adapter for DB (port UserRepository)
     */
    @Bean
    public TaskRepository taskRepository(
            TaskJpaRepository taskJpaRepository,
            art.lapov.adapterdb.TaskMapper taskMapper) {
        return new TaskRepositoryAdapter(taskJpaRepository, taskMapper);
    }

    /**
     * Use cases realisation
     */
    @Bean
    public TaskService taskService(
            TaskRepository taskRepository,
            art.lapov.application.mapper.TaskMapper taskMapper) {
        return new TaskService(taskRepository, taskMapper);
    }

}