package art.lapov.adapterdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, String> {
    List<TaskEntity> findAllByUserId(String userId);
}
