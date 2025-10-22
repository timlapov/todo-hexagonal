package art.lapov.domain.port.out;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> getById(UserId id);
    List<User> getAll();
    void delete(UserId id);
}
