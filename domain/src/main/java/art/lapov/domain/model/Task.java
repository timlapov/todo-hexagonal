package art.lapov.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Task {
    private final TaskId id;
    private String name;
    private String description;
    private String status;
    private final Instant createdAt;
    private Instant updatedAt;
    private final UserId userId;

    public Task(String name, String description, String userId) {
        this.id = new TaskId(UUID.randomUUID().toString());
        this.name = name;
        this.description = description;
        this.status = TaskStatus.OPEN.name();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.userId = new UserId(userId);
    }

    public Task(String id, String name, String description, String status, Instant createdAt, Instant updatedAt, String userId) {
        this.id = new TaskId(id);
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = new UserId(userId);
    }

    public void complete() {
        if (this.status.equals(TaskStatus.COMPLETED.name())) {
            throw new IllegalStateException("Task is already completed");
        }
        this.status = TaskStatus.COMPLETED.name();
        this.updatedAt = Instant.now();
    }

    public void cancel() {
        if (this.status.equals(TaskStatus.CANCELLED.name())) {
            throw new IllegalStateException("Task is already cancelled");
        }
        if (this.status.equals(TaskStatus.COMPLETED.name())) {
            throw new IllegalStateException("Task is already completed and can't be cancelled");
        }
        this.status = TaskStatus.CANCELLED.name();
        this.updatedAt = Instant.now();
    }

    public void inProgress() {
        if (this.status.equals(TaskStatus.OPEN.name())) {
            this.status = TaskStatus.IN_PROGRESS.name();
            this.updatedAt = Instant.now();
        } else {
            throw new IllegalStateException("Task is not open");
        }
    }

    public void isOwner(UserId userId) {
        if (!this.userId.equals(userId)) {
            throw new IllegalStateException("Task is not owned by user");
        }
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public TaskId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                '}';
    }
}
