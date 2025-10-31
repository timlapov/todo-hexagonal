package art.lapov.domain.port.in;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface FindUsersUseCase {
    List<User> getAllUsers();
    User getUserById(UserId id);
}
