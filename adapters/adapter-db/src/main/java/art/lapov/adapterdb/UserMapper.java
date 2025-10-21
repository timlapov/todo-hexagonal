package art.lapov.adapterdb;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;

public class UserMapper {
    public UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId().toString(),
                user.getFirtName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    public User toDomain(UserEntity entity) {
        return new User(
                new UserId(entity.getId()),
                entity.getFirtName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }
}
