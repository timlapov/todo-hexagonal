package art.lapov.adapterdb;

import art.lapov.domain.model.TaskId;
import art.lapov.domain.model.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String userId;

//    public TaskEntity(String id, String name, String description, String status, Instant createdAt, Instant updatedAt, String userId) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.userId = userId;
//    }
}
