package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request to change task priority")
public class PriorityRequest {
    @Schema(description = "Priority of the task", allowableValues = {"LOW", "MEDIUM", "HIGH"}, example = "HIGH")
    @NotNull(message = "Priority cannot be null")
    @NotBlank(message = "Priority cannot be blank")
    private String priority;
}
