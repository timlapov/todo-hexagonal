package art.lapov.domain.port.out;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User getById(UserId id);
    List<User> getAll();
    void delete(UserId id);
}
