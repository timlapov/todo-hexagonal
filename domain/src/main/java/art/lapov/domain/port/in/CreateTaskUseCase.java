package art.lapov.domain.port.in;

import art.lapov.domain.model.Task;
import art.lapov.domain.model.UserId;

public interface CreateTaskUseCase {
    Task createTask(String name, String description, UserId userId);
}
