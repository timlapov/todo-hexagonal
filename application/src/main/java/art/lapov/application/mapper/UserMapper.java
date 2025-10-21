package art.lapov.application.mapper;

import art.lapov.application.dto.UserResponse;
import art.lapov.domain.model.User;

public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId().toString())
                .firtName(user.getFirtName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
