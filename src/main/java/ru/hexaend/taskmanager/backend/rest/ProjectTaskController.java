package ru.hexaend.taskmanager.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hexaend.taskmanager.backend.dto.request.ChangeTaskRequest;
import ru.hexaend.taskmanager.backend.dto.request.PriorityRequest;
import ru.hexaend.taskmanager.backend.dto.request.StatusRequest;
import ru.hexaend.taskmanager.backend.dto.request.TaskRequest;
import ru.hexaend.taskmanager.backend.dto.response.ProjectResponse;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;
import ru.hexaend.taskmanager.backend.service.ProjectService;
import ru.hexaend.taskmanager.backend.service.UserService;


@RequestMapping("/api/projects/{projectId}/tasks")
@PreAuthorize("hasRole('USER')")
@RestController
@RequiredArgsConstructor
@Tag(name="Tasks", description = "Project task operations")
public class ProjectTaskController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get project tasks",
            description = "Get all tasks of the project. Users can access this endpoint.")
    public ResponseEntity<?> getProject(@PathVariable(name = "projectId") Long projectId) {
        String sub = userService.getSubFromJwt();
        ProjectResponse projectResponse = projectService.getProject(projectId, sub);
        return ResponseEntity.ok(projectResponse);
    }

    @PostMapping
    @Operation(summary = "Create task",
            description = "Create a new task in the project. Users can access this endpoint.")
    public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest, @PathVariable(name = "projectId") Long projectId) {
        String sub = userService.getSubFromJwt();
        return projectService.createTask(taskRequest, projectId, sub);
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "Update task",
            description = "Update an existing task in the project. Users can access this endpoint.")
    public TaskResponse updateTask(@PathVariable(name = "taskId") Long taskId, @Valid @RequestBody ChangeTaskRequest taskRequest, @PathVariable Long projectId) {
        String sub = userService.getSubFromJwt();
        return projectService.updateTask(taskId, projectId, taskRequest, sub);
    }

    @PatchMapping("/{taskId}/status")
    @Operation(summary = "Update task status",
            description = "Update the status of an existing task in the project. Users can access this endpoint.")
    public TaskResponse updateTask(@PathVariable(name = "taskId") Long taskId, @Valid @RequestBody StatusRequest statusRequest, @PathVariable Long projectId) {
        String sub = userService.getSubFromJwt();
        return projectService.setStatusTask(taskId, projectId, statusRequest, sub);
    }

    @PatchMapping("/{taskId}/priority")
    @Operation(summary = "Update task priority",
            description = "Update the priority of an existing task in the project. Users can access this endpoint.")
    public TaskResponse updateTask(@PathVariable(name = "taskId") Long taskId, @Valid @RequestBody PriorityRequest priorityRequest, @PathVariable Long projectId) {
        String sub = userService.getSubFromJwt();
        return projectService.setPriority(taskId, projectId, priorityRequest, sub);
    }


}
