package art.lapov.domain.port.in;

import art.lapov.domain.model.UserId;

public interface DeleteUserUseCase {
    void deleteUser(UserId id);
}
