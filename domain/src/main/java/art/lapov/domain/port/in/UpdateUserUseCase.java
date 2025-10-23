package art.lapov.domain.port.in;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

public interface UpdateUserUseCase {
    User updateUser(UserId id, String firstName, String lastName, String email);
}
