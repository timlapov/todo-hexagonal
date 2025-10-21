package art.lapov.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TaskResponse {
    private String id;
    private String name;
    private String description;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private String ownerId;
}
