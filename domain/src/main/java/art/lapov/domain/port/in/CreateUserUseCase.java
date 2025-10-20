package art.lapov.domain.port.in;

import art.lapov.domain.model.User;

public interface CreateUserUseCase {
    User createUser(String firtName, String lastName, String email);
}
