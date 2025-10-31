package art.lapov.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserId Value Object Tests")
class UserIdTest {

    @Test
    @DisplayName("Should create UserId with valid UUID string")
    void shouldCreateUserIdWithValidUuid() {
        // Given
        String validUuid = UUID.randomUUID().toString();

        // When
        UserId userId = new UserId(validUuid);

        // Then
        assertThat(userId.getValue()).isEqualTo(validUuid);
    }

    @Test
    @DisplayName("Should generate new UserId with valid UUID")
    void shouldGenerateNewUserId() {
        // When
        UserId userId = UserId.generate();

        // Then
        assertThat(userId.getValue()).isNotNull();
        assertDoesNotThrow(() -> UUID.fromString(userId.getValue()));
    }

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        // When & Then
        assertThatThrownBy(() -> new UserId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("UserId cannot be null");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is blank")
    void shouldThrowExceptionWhenValueIsBlank() {
        // When & Then
        assertThatThrownBy(() -> new UserId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("UserId cannot be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is only whitespace")
    void shouldThrowExceptionWhenValueIsWhitespace() {
        // When & Then
        assertThatThrownBy(() -> new UserId("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("UserId cannot be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is not valid UUID")
    void shouldThrowExceptionWhenValueIsNotValidUuid() {
        // When & Then
        assertThatThrownBy(() -> new UserId("not-a-valid-uuid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("UserId must be a valid UUID format");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is numeric but not UUID")
    void shouldThrowExceptionWhenValueIsNumericButNotUuid() {
        // When & Then
        assertThatThrownBy(() -> new UserId("12345"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("UserId must be a valid UUID format");
    }

    @Test
    @DisplayName("Should return true for equals when UserIds have same value")
    void shouldBeEqualWhenSameValue() {
        // Given
        String uuid = UUID.randomUUID().toString();
        UserId userId1 = new UserId(uuid);
        UserId userId2 = new UserId(uuid);

        // When & Then
        assertThat(userId1).isEqualTo(userId2);
        assertThat(userId1.hashCode()).isEqualTo(userId2.hashCode());
    }

    @Test
    @DisplayName("Should return false for equals when UserIds have different values")
    void shouldNotBeEqualWhenDifferentValues() {
        // Given
        UserId userId1 = new UserId(UUID.randomUUID().toString());
        UserId userId2 = new UserId(UUID.randomUUID().toString());

        // When & Then
        assertThat(userId1).isNotEqualTo(userId2);
    }

    @Test
    @DisplayName("Should return true for equals when comparing same instance")
    void shouldBeEqualWhenSameInstance() {
        // Given
        UserId userId = new UserId(UUID.randomUUID().toString());

        // When & Then
        assertThat(userId).isEqualTo(userId);
    }

    @Test
    @DisplayName("Should return false for equals when comparing with null")
    void shouldNotBeEqualToNull() {
        // Given
        UserId userId = new UserId(UUID.randomUUID().toString());

        // When & Then
        assertThat(userId).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Should return false for equals when comparing with different class")
    void shouldNotBeEqualToDifferentClass() {
        // Given
        UserId userId = new UserId(UUID.randomUUID().toString());
        String string = "some-string";

        // When & Then
        assertThat(userId).isNotEqualTo(string);
    }

    @Test
    @DisplayName("Should return UUID string in toString")
    void shouldReturnValueInToString() {
        // Given
        String uuid = UUID.randomUUID().toString();
        UserId userId = new UserId(uuid);

        // When
        String result = userId.toString();

        // Then
        assertThat(result).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Should have same hashCode for equal UserIds")
    void shouldHaveSameHashCodeForEqualUserIds() {
        // Given
        String uuid = UUID.randomUUID().toString();
        UserId userId1 = new UserId(uuid);
        UserId userId2 = new UserId(uuid);

        // When & Then
        assertThat(userId1.hashCode()).isEqualTo(userId2.hashCode());
    }
}