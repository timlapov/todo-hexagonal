package art.lapov.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskId Value Object Tests")
class TaskIdTest {

    @Test
    @DisplayName("Should create TaskId with valid UUID string")
    void shouldCreateTaskIdWithValidUuid() {
        // Given
        String validUuid = UUID.randomUUID().toString();

        // When
        TaskId taskId = new TaskId(validUuid);

        // Then
        assertThat(taskId.getValue()).isEqualTo(validUuid);
    }

    @Test
    @DisplayName("Should generate new TaskId with valid UUID")
    void shouldGenerateNewTaskId() {
        // When
        TaskId taskId = TaskId.generate();

        // Then
        assertThat(taskId.getValue()).isNotNull();
        assertDoesNotThrow(() -> UUID.fromString(taskId.getValue()));
    }

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        // When & Then
        assertThatThrownBy(() -> new TaskId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("TaskId cannot be null");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is blank")
    void shouldThrowExceptionWhenValueIsBlank() {
        // When & Then
        assertThatThrownBy(() -> new TaskId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskId cannot be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is only whitespace")
    void shouldThrowExceptionWhenValueIsWhitespace() {
        // When & Then
        assertThatThrownBy(() -> new TaskId("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskId cannot be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is not valid UUID")
    void shouldThrowExceptionWhenValueIsNotValidUuid() {
        // When & Then
        assertThatThrownBy(() -> new TaskId("not-a-valid-uuid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskId must be a valid UUID format");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is numeric but not UUID")
    void shouldThrowExceptionWhenValueIsNumericButNotUuid() {
        // When & Then
        assertThatThrownBy(() -> new TaskId("12345"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskId must be a valid UUID format");
    }

    @Test
    @DisplayName("Should return true for equals when TaskIds have same value")
    void shouldBeEqualWhenSameValue() {
        // Given
        String uuid = UUID.randomUUID().toString();
        TaskId taskId1 = new TaskId(uuid);
        TaskId taskId2 = new TaskId(uuid);

        // When & Then
        assertThat(taskId1).isEqualTo(taskId2);
        assertThat(taskId1.hashCode()).isEqualTo(taskId2.hashCode());
    }

    @Test
    @DisplayName("Should return false for equals when TaskIds have different values")
    void shouldNotBeEqualWhenDifferentValues() {
        // Given
        TaskId taskId1 = new TaskId(UUID.randomUUID().toString());
        TaskId taskId2 = new TaskId(UUID.randomUUID().toString());

        // When & Then
        assertThat(taskId1).isNotEqualTo(taskId2);
    }

    @Test
    @DisplayName("Should return true for equals when comparing same instance")
    void shouldBeEqualWhenSameInstance() {
        // Given
        TaskId taskId = new TaskId(UUID.randomUUID().toString());

        // When & Then
        assertEquals(taskId, taskId);
    }

    @Test
    @DisplayName("Should return false for equals when comparing with null")
    void shouldNotBeEqualToNull() {
        // Given
        TaskId taskId = new TaskId(UUID.randomUUID().toString());

        // When & Then
        assertThat(taskId).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Should return false for equals when comparing with different class")
    void shouldNotBeEqualToDifferentClass() {
        // Given
        TaskId taskId = new TaskId(UUID.randomUUID().toString());
        String string = "some-string";

        // When & Then
        assertThat(taskId).isNotEqualTo(string);
    }

    @Test
    @DisplayName("Should return UUID string in toString")
    void shouldReturnValueInToString() {
        // Given
        String uuid = UUID.randomUUID().toString();
        TaskId taskId = new TaskId(uuid);

        // When
        String result = taskId.toString();

        // Then
        assertThat(result).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Should have same hashCode for equal TaskIds")
    void shouldHaveSameHashCodeForEqualTaskIds() {
        // Given
        String uuid = UUID.randomUUID().toString();
        TaskId taskId1 = new TaskId(uuid);
        TaskId taskId2 = new TaskId(uuid);

        // When & Then
        assertThat(taskId1.hashCode()).isEqualTo(taskId2.hashCode());
    }
}