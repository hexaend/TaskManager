package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Request to change project")
public class ChangeProjectRequest {

    @Schema(description = "Name of the project", example = "Project 1")
    private String name;

    @Schema(description = "Description of the project", example = "This is a sample project")
    private String description;

    @AssertTrue(message = "At least one field must be filled")
    private boolean isAtLeastOneFieldFilled() {
        return name != null && !name.trim().isEmpty() ||
                description != null && !description.trim().isEmpty();
    }


}
