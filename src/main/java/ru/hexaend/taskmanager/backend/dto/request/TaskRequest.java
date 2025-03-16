package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.hexaend.taskmanager.backend.model.enums.TaskPriority;
import ru.hexaend.taskmanager.backend.model.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Request to create a task")
public class TaskRequest {

    @Schema(description = "Title of the task", example = "Task 1")
    @NotBlank(message = "Task title cannot be blank")
    @NotNull(message = "Task title cannot be null")
    private String title;

    @Schema(description = "Description of the task", example = "This is a sample task")
    @NotBlank(message = "Task description cannot be blank")
    @NotNull(message = "Task description cannot be null")
    private String description;

    @Schema(description = "Status of the task", allowableValues = {"NEW", "IN_PROGRESS", "DONE"}, example = "NEW")
    @NotNull(message = "Task status cannot be null")
    private TaskStatus status;

    @Schema(description = "Priority of the task", allowableValues = {"LOW", "MEDIUM", "HIGH"}, example = "HIGH")
    @NotNull(message = "Task priority cannot be null")
    private TaskPriority priority;

    @Schema(description = "Deadline of the task", example = "2025-01-01T10:00:00Z")
    @NotNull(message = "Task deadline cannot be null")
    @Future(message = "Task deadline must be in the future")
    private LocalDateTime deadline;

}
