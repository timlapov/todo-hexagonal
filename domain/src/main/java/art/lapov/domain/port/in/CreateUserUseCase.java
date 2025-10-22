package art.lapov.domain.port.in;

import art.lapov.domain.model.User;

public interface CreateUserUseCase {
    User createUser(String firstName, String lastName, String email);
}
