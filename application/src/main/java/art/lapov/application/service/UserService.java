package art.lapov.application.service;

import art.lapov.application.mapper.UserMapper;
import art.lapov.domain.exception.UserNotFoundException;
import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.DeleteUserUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
import art.lapov.domain.port.in.UpdateUserUseCase;
import art.lapov.domain.port.out.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    public User createUser(String firstName, String lastName, String email) {
        checkEmailIsNotNullAndIsNotBlank(email);
        User user = new User(firstName, lastName, email);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserId id) {
        checkIdIsNotNull(id);
        checkUserExists(id);
        userRepository.delete(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getUserById(UserId id) {
        checkIdIsNotNull(id);
        return userRepository.getById(id);
    }

    @Override
    public User updateUser(UserId id, String firstName, String lastName, String email) {
        checkUserExists(id);
        validateUpdateUserRequest(email, id);
        checkUserExists(id);
        User user = userRepository.getById(id).orElseThrow();
        user.setEmail(email);
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        return userRepository.save(user);
    }

    private static void checkIdIsNotNull(UserId id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
        }
    }

    private static void checkEmailIsNotNullAndIsNotBlank(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email is required");
        }
    }

    private void validateUpdateUserRequest(String email, UserId id) {
        checkEmailIsNotNullAndIsNotBlank(email);
        checkIdIsNotNull(id);
    }

    private void checkUserExists(UserId id) {
        checkIdIsNotNull(id);
        Optional<User> user = userRepository.getById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
    }
}
