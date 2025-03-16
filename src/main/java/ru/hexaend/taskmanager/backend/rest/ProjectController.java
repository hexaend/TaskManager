package ru.hexaend.taskmanager.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hexaend.taskmanager.backend.dto.request.ChangeProjectRequest;
import ru.hexaend.taskmanager.backend.dto.request.ProjectRequest;
import ru.hexaend.taskmanager.backend.dto.response.ProjectResponse;
import ru.hexaend.taskmanager.backend.service.ProjectService;
import ru.hexaend.taskmanager.backend.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
@Tag(name = "Projects", description = "Project operations")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create new project",
            description = "Create new project. Only managers can create projects.")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.createProject(projectRequest, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @PatchMapping("/{projectId}")
    @Operation(summary = "Update project",
            description = "Update project. Only managers can update projects.")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @Valid @RequestBody ChangeProjectRequest projectRequest) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.updateProject(projectId, projectRequest, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @Operation(summary = "Get project by id",
            description = "Get project by id. Only managers can get projects.")
    @PutMapping("/{projectId}/add-user")
    public ResponseEntity<?> addUserToProject(@PathVariable Long projectId, @RequestParam String username) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.addUserToProject(projectId, username, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @PutMapping("/{projectId}/add-manager")
    @Operation(summary = "Add manager to project",
            description = "Add manager to project. Only managers can add managers to projects.")
    public ResponseEntity<?> addManagerToProject(@PathVariable Long projectId, @RequestParam String username) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.addManagerToProject(projectId, username, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @DeleteMapping("/{projectId}/remove-user")
    @Operation(summary = "Remove user from project",
            description = "Remove user from project. Only managers can remove users from projects.")
    @Schema(description = "Remove user from project")
    public ResponseEntity<?> removeUserFromProject(@PathVariable Long projectId, @RequestParam String username) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.removeUserFromProject(projectId, username, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Remove project",
            description = "Remove project. Only managers can remove projects.")
    public ResponseEntity<?> removeProject(@PathVariable Long projectId) {
        String sub = userService.getSubFromJwt();
        projectService.removeProject(projectId, sub);
        return ResponseEntity.ok("Project removed");
    }


    @DeleteMapping("/{projectId}/{taskId}")
    @Operation(summary = "Remove task from project",
            description = "Remove task from project. Only managers can remove tasks from projects.")
    public ResponseEntity<?> removeTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        String sub = userService.getSubFromJwt();
        projectService.removeTask(projectId, taskId, sub);
        return ResponseEntity.ok("Task removed");
    }

}
