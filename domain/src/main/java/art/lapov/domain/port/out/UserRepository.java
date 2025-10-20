package art.lapov.domain.port.out;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

public interface UserRepository {
    User save(User user);
    User getById(UserId id);
    void delete(UserId id);
}
