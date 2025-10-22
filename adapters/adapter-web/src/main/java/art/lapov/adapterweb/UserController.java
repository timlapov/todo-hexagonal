package art.lapov.adapterweb;

import art.lapov.application.dto.CreateUserRequest;
import art.lapov.application.dto.UserResponse;
import art.lapov.application.mapper.UserMapper;
import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.in.CreateUserUseCase;
import art.lapov.domain.port.in.DeleteUserUseCase;
import art.lapov.domain.port.in.FindUsersUseCase;
import art.lapov.domain.port.in.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUsersUseCase findUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<User> userList = findUsersUseCase.getAllUsers();
        List<UserResponse> userResponses = userList.stream().map(userMapper::toUserResponse).toList();
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable String id) {
        Optional<User> user = findUsersUseCase.getUserById(new UserId(id));
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toUserResponse(user.get()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        User user = createUserUseCase.createUser(request.getFirstName(), request.getLastName(), request.getEmail());
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteUserUseCase.deleteUser(new UserId(id));
    }

}
