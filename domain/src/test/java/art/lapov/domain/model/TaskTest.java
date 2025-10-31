package art.lapov.domain.model;

import art.lapov.domain.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Task Object Test")
public class TaskTest {

    @Test
    @DisplayName("A new task should be created with all the valid parameters")
    void shouldCreateNewTask() {
//        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());

//        Then
        assertNotNull(task);
        assertEquals("TaskName", task.getName());
        assertEquals("TaskDescription", task.getDescription());
        assertThat(task.getId().getValue()).isNotNull();
        assertThat(task.getUserId().getValue()).isNotNull();
        assertDoesNotThrow(() -> UUID.fromString(task.getId().getValue()));
        assertDoesNotThrow(() -> UUID.fromString(task.getUserId().getValue()));
        assertTrue(Instant.now().isAfter(task.getCreatedAt()));
        assertEquals(task.getCreatedAt(), task.getUpdatedAt()); //TODO QUESTION FOR RAMI
        assertEquals(TaskStatus.OPEN.toString(), task.getStatus());
    }

    @Test
    @DisplayName("Verification of correct status change")
    void shouldChangeStatusOfTask() {
//        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());
        Task task2 = new Task("Task2Name", "Task2Description", UUID.randomUUID().toString());

//        Then
        assertEquals(TaskStatus.OPEN.toString(), task.getStatus());

        task.inProgress();
        assertEquals(TaskStatus.IN_PROGRESS.toString(), task.getStatus());

        task.complete();
        assertEquals(TaskStatus.COMPLETED.toString(), task.getStatus());

        assertEquals(TaskStatus.OPEN.toString(), task2.getStatus());
        task2.cancel();
        assertEquals(TaskStatus.CANCELLED.toString(), task2.getStatus());
    }

    @Test
    @DisplayName("A completed task cannot be canceled")
    void shouldThrowExceptionWhenAttemptingToCancelCompletedTAsk() {
//        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());
        task.complete();

//        Then
        assertThrows(IllegalStateException.class, task::cancel);
    }

    @Test
    @DisplayName("A cancelled task cannot be completed")
    void shouldThrowExceptionWhenAttemptingToCompleteCancelledTask() {
//        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());
        task.cancel();

//        Then
        assertThrows(IllegalStateException.class, task::complete);
    }

    @Test
    @DisplayName("A cancelled task cannot be in progress")
    void shouldThrowExceptionWhenAttemptingToInProgressCancelledTask() {
//        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());
        task.cancel();

//        Then
        assertThrows(IllegalStateException.class, task::inProgress);
    }

    @Test
    @DisplayName("A completed task cannot be in progress")
    void shouldThrowExceptionWhenAttemptingToInProgressCompletedTask() {
        //        When
        Task task = new Task("TaskName", "TaskDescription", UUID.randomUUID().toString());
        task.complete();

//        Then
        assertThrows(IllegalStateException.class, task::inProgress);
    }

    @Test
    @DisplayName("A new task should not be created with invalid UserID")
    void shouldNotCreateNewTaskWithInvalidUserId() {
        assertThrows(IllegalArgumentException.class, () -> new Task("TaskName", "TaskDescription", "invalidUserId"));
    }

    @Test
    @DisplayName("A new task should not be created with invalid name")
    void shouldNotCreateNewTaskWithInvalidName() {
        assertThrows(InvalidInputException.class, () -> new Task(" ", "TaskDescription", UUID.randomUUID().toString()));
        assertThrows(InvalidInputException.class, () -> new Task(null, "TaskDescription", UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("A new task should not be created with invalid description")
    void shouldNotCreateNewTaskWithInvalidDescription() {
        assertThrows(InvalidInputException.class, () -> new Task("TaskName", " ", UUID.randomUUID().toString()));
        assertThrows(InvalidInputException.class, () -> new Task("TaskName", null, UUID.randomUUID().toString()));
    }
}
