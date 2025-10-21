package art.lapov.domain.model;

import java.util.Objects;
import java.util.UUID;

public class TaskId {

    private final String value;

    public TaskId(String value) {
        Objects.requireNonNull(value, "TaskId cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("TaskId cannot be blank");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("TaskId must be a valid UUID format: " + value, e);
        }

        this.value = value;
    }

    public static TaskId generate() {
        return new TaskId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId = (TaskId) o;
        return Objects.equals(value, taskId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}