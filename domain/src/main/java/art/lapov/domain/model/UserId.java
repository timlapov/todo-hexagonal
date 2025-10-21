package art.lapov.domain.model;

import java.util.Objects;
import java.util.UUID;

public class UserId {

    private final String value;

    public UserId(String value) {
        Objects.requireNonNull(value, "UserId cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be blank");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UserId must be a valid UUID format: " + value, e);
        }

        this.value = value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
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
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
