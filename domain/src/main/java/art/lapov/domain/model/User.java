package art.lapov.domain.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    UserId id;
    String firtName;
    String lastName;
    String email;

    public User(String firtName, String lastName, String email) {
        this.id = new UserId(UUID.randomUUID().toString());
        this.firtName = firtName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(UserId id, String firtName, String lastName, String email) {
        this.id = id;
        this.firtName = firtName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firtName, user.firtName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firtName, lastName, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firtName='" + firtName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
