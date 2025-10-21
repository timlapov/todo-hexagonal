package art.lapov.application.service;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.DeleteUserUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
import art.lapov.domain.port.in.UpdateUserUseCase;
import art.lapov.domain.port.out.UserRepository;

import java.util.List;

public class UserService implements CreateUserUseCase, FindUsersUseCase, UpdateUserUseCase, DeleteUserUseCase {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String firtName, String lastName, String email) {
        return null;
    }

    @Override
    public void deleteUser(UserId id) {

    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(UserId id) {
        return null;
    }

    @Override
    public User updateUser(UserId id, String firtName, String lastName, String email) {
        return null;
    }
}
