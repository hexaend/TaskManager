package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Data;
import ru.hexaend.taskmanager.backend.model.enums.TaskPriority;
import ru.hexaend.taskmanager.backend.model.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Request to change task")
public class ChangeTaskRequest {

    @Schema(description = "Name of the task", example = "Task 1")
    private String name;

    @Schema(description = "Description of the task", example = "This is a sample task")
    private String description;

    @Schema(description = "Status of the task", allowableValues = {"NEW", "IN_PROGRESS", "DONE"}, example = "NEW")
    private TaskStatus status;

    @Schema(description = "Priority of the task", allowableValues = {"LOW", "MEDIUM", "HIGH"}, example = "HIGH")
    private TaskPriority priority;

    @Schema(description = "Deadline of the task", example = "2025-01-01T10:00:00Z")
    private LocalDateTime deadline;

    // TODO: add status
    // TODO: add priority
    // TODO: add deadline


    @AssertTrue(message = "At least one field must be filled")
    private boolean isAtLeastOneFieldFilled() {
        return name != null && !name.trim().isEmpty() ||
                description != null && !description.trim().isEmpty() ||
                status != null ||
                priority != null ||
                deadline != null;
    }

}
