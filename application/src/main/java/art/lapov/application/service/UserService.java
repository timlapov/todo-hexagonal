package art.lapov.application.service;

import art.lapov.application.mapper.UserMapper;
import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.DeleteUserUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
import art.lapov.domain.port.in.UpdateUserUseCase;
import art.lapov.domain.port.out.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService implements CreateUserUseCase, FindUsersUseCase, UpdateUserUseCase, DeleteUserUseCase {

    public final UserRepository userRepository;
    public final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(String firtName, String lastName, String email) {
        validateCreateUserRequest(email);
        User user = new User(firtName, lastName, email);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserId id) {

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getUserById(UserId id) {
        if (id == null) {
            return null;
        }
        return userRepository.getById(id);
    }

    @Override
    public User updateUser(UserId id, String firtName, String lastName, String email) {
        return null;
    }

    private void validateCreateUserRequest(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("User email is required");
        }
    }
}
