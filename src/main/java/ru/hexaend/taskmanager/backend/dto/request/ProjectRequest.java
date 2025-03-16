package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.hexaend.taskmanager.backend.model.Project;
import ru.hexaend.taskmanager.backend.model.User;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Schema(description = "Request to create a new project")
public class ProjectRequest {

    @Schema(description = "Name of the project", example = "Project 1")
    @NotNull(message = "Project name cannot be null")
    @NotBlank(message = "Project name cannot be blank")
    private String name;

    @Schema(description = "Description of the project", example = "This is a sample project")
    @NotNull(message = "Project description cannot be null")
    @NotBlank(message = "Project description cannot be blank")
    private String description;

    public Project toProject(User user) {
        Set<User> managers = new HashSet<>();
        managers.add(user);

        return Project.builder()
                .managerList(managers)
                .name(name)
                .description(description)
                .build();
    }
}
