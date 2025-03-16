package ru.hexaend.taskmanager.backend.service;

import ru.hexaend.taskmanager.backend.dto.request.*;
import ru.hexaend.taskmanager.backend.dto.response.ProjectResponse;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;

public interface ProjectService {


    ProjectResponse createProject(ProjectRequest projectRequest, String keycloakId);

    ProjectResponse updateProject(Long projectId, ChangeProjectRequest projectRequest, String sub);

    ProjectResponse addUserToProject(Long projectId, String username, String sub);

    ProjectResponse getProject(Long projectId, String sub);

    ProjectResponse removeUserFromProject(Long projectId, String username, String sub);

    TaskResponse createTask(TaskRequest taskRequest, Long projectId, String sub);

    TaskResponse updateTask(Long taskId, Long projectId, ChangeTaskRequest taskRequest, String sub);

    ProjectResponse addManagerToProject(Long projectId, String username, String sub);

    TaskResponse setStatusTask(Long taskId, Long projectId, StatusRequest statusRequest, String sub);

    TaskResponse setPriority(Long taskId, Long projectId, PriorityRequest statusRequest, String sub);

    void removeProject(Long projectId, String sub);

    void removeTask(Long projectId, Long taskId, String sub);
}
