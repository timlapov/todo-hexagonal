package art.lapov.adapterdb;

import art.lapov.domain.model.User;
import art.lapov.domain.model.UserId;
import art.lapov.domain.port.out.UserRepository;

import java.util.List;

public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        UserEntity savedUser = userJpaRepository.save(userEntity);
        return userMapper.toDomain(savedUser);
    }

    @Override
    public User getById(UserId id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userJpaRepository.findAll().stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UserId id) {

    }
}
