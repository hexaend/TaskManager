package ru.hexaend.taskmanager.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hexaend.taskmanager.backend.model.Task;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@Schema(description = "Response for task")
public class TaskResponse {

    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Task 1")
    private String title;

    @Schema(description = "Task description", example = "This is a sample task")
    private String description;

    @Schema(description = "Task status", allowableValues = { "NEW", "IN_PROGRESS", "DONE" }, example = "NEW")
    private String status;

    @Schema(description = "Task priority", allowableValues = { "LOW", "MEDIUM", "HIGH" }, example = "HIGH")
    private String priority;

    @Schema(description = "Task creation date", example = "2025-01-01T12:00:00Z")
    private String createdAt;

    @Schema(description = "Task update date", example = "2025-01-01T12:00:00Z")
    private String updatedAt;

    @Schema(description = "Project details", implementation = ProjectResponse.class)
    private ProjectResponse project;

    @Schema(description = "Task deadline", example = "2025-01-01T12:00:00Z")
    private String deadline;

    public static TaskResponse fromTask(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .priority(task.getPriority().name())
                .createdAt(task.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .updatedAt(task.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .deadline(task.getDeadline().format(DateTimeFormatter.ISO_DATE_TIME))
                .project(ProjectResponse.fromProject(task.getProject()))
                .build();
    }
}