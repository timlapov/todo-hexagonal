package art.lapov.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String firtName;
    private String lastName;
    private String email;
}
