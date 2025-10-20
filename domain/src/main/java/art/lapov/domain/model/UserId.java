package art.lapov.domain.model;

import java.util.Objects;

public class UserId {
    public UserId(String id) {
        Objects.requireNonNull(id, "id must not be null");
    }
}
