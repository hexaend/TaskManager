package ru.hexaend.taskmanager.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hexaend.taskmanager.backend.model.Project;
import ru.hexaend.taskmanager.backend.model.Task;
import ru.hexaend.taskmanager.backend.model.User;

import java.util.Collections;
import java.util.List;

@Builder
@Data
@Schema(description = "Response for project details")
public class ProjectResponse {

    @Schema(description = "Project ID", example = "1")
    private Long id;

    @Schema(description = "Project name", example = "Project 1")
    private String name;

    @Schema(description = "List of project managers", example = "[\"manager1\", \"manager2\"]")
    private List<String> managers;

    @Schema(description = "Project description", example = "This is a sample project")
    private String description;

    @Schema(description = "List of users in the project", example = "[\"user1\", \"user2\"]")
    private List<String> users;

    @Schema(description = "List of tasks in the project", example = "[\"task1\", \"task2\"]")
    private List<String> tasks;

    public static ProjectResponse fromProject(Project project) {
        if (project == null) {
            return null;
        }

        return ProjectResponse.builder()
                .managers(project.getManagerList() != null
                        ? project.getManagerList().stream().map(User::getUsername).toList()
                        : Collections.emptyList())
                .name(project.getName())
                .description(project.getDescription())
                .id(project.getId())
                .users(project.getUsers() != null
                        ? project.getUsers().stream().map(User::getUsername).toList()
                        : Collections.emptyList())
                .tasks(project.getTasks() != null
                        ? project.getTasks().stream().map(Task::getTitle).toList()
                        : Collections.emptyList())
                .build();
    }
}
