package art.lapov.domain.model;

import java.util.Objects;

public class TaskId {
    public TaskId(String id) {
        Objects.requireNonNull(id, "id must not be null");
    }
}
