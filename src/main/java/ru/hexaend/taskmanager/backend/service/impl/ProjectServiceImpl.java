package ru.hexaend.taskmanager.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hexaend.taskmanager.backend.dto.request.*;
import ru.hexaend.taskmanager.backend.dto.response.ProjectResponse;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;
import ru.hexaend.taskmanager.backend.exception.AccessException;
import ru.hexaend.taskmanager.backend.exception.ProjectNotFoundException;
import ru.hexaend.taskmanager.backend.exception.TaskNotFoundException;
import ru.hexaend.taskmanager.backend.model.Project;
import ru.hexaend.taskmanager.backend.model.Task;
import ru.hexaend.taskmanager.backend.model.User;
import ru.hexaend.taskmanager.backend.model.enums.TaskPriority;
import ru.hexaend.taskmanager.backend.model.enums.TaskStatus;
import ru.hexaend.taskmanager.backend.repository.ProjectRepository;
import ru.hexaend.taskmanager.backend.repository.TaskRepository;
import ru.hexaend.taskmanager.backend.repository.UserRepository;
import ru.hexaend.taskmanager.backend.service.ProjectService;

import java.time.LocalDateTime;
import java.util.Objects;


@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest, String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Project project = projectRepository.save(projectRequest.toProject(user));
        return ProjectResponse.fromProject(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long projectId, ChangeProjectRequest projectRequest, String sub) {
        // TODO: self exception
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        if (projectRequest.getName() != null)
            project.setName(projectRequest.getName());
        if (projectRequest.getDescription() != null)
            project.setDescription(projectRequest.getDescription());

        projectRepository.save(project);
        return ProjectResponse.fromProject(project);
    }

    @Override
    @Transactional
    public ProjectResponse addUserToProject(Long projectId, String username, String sub) {
        // TODO: self exception
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        project.addUser(user);
        projectRepository.save(project);
        return ProjectResponse.fromProject(project);
    }

    @Override
    public ProjectResponse getProject(Long projectId, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        return ProjectResponse.fromProject(project);
    }

    @Override
    @Transactional
    public ProjectResponse removeUserFromProject(Long projectId, String username, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        project.removeUser(user);
        projectRepository.save(project);

        return ProjectResponse.fromProject(project);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest, Long projectId, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        Task task = Task.fromRequest(taskRequest, project);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
        project.addTask(task);
        projectRepository.save(project);

        return TaskResponse.fromTask(task);
    }


    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, Long projectId, ChangeTaskRequest taskRequest, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!Objects.equals(task.getProject().getId(), project.getId())) return null;

        if (taskRequest.getName() != null)
            task.setTitle(taskRequest.getName());
        if (taskRequest.getDescription() != null)
            task.setDescription(taskRequest.getDescription());
        if (task.getDeadline() != null)
            task.setDeadline(taskRequest.getDeadline());
        if (taskRequest.getPriority() != null)
            task.setPriority(taskRequest.getPriority());
        if (taskRequest.getStatus() != null)
            task.setStatus(taskRequest.getStatus());

        task = taskRepository.save(task);
        return TaskResponse.fromTask(task);
    }

    @Override
    @Transactional
    public ProjectResponse addManagerToProject(Long projectId, String username, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        validateManagerAccess(project, sub);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        project.addManager(user);
        projectRepository.save(project);
        return ProjectResponse.fromProject(project);
    }

    @Override
    @Transactional
    public TaskResponse setStatusTask(Long taskId, Long projectId, StatusRequest statusRequest, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!Objects.equals(task.getProject().getId(), project.getId())) throw new AccessException("Access denied");

        if (statusRequest.getStatus() != null)
            task.setStatus(TaskStatus.valueOf(statusRequest.getStatus()));

        task = taskRepository.save(task);
        return TaskResponse.fromTask(task);
    }

    @Override
    @Transactional
    public TaskResponse setPriority(Long taskId, Long projectId, PriorityRequest statusRequest, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!Objects.equals(task.getProject().getId(), project.getId())) throw new AccessException("Access denied");

        if (statusRequest.getPriority() != null)
            task.setPriority(TaskPriority.valueOf(statusRequest.getPriority()));

        task = taskRepository.save(task);
        return TaskResponse.fromTask(task);
    }

    @Override
    public void removeProject(Long projectId, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);
        projectRepository.delete(project);
    }

    @Override
    public void removeTask(Long projectId, Long taskId, String sub) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        validateManagerAccess(project, sub);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ProjectNotFoundException("Task not found"));

        if (!Objects.equals(task.getProject().getId(), project.getId())) throw new AccessException("Access denied");

        taskRepository.delete(task);
    }

    private void validateManagerAccess(Project project, String keycloakId) {
        if (project.getManagerList().stream().noneMatch(user -> user.getKeycloakId().equals(keycloakId))) {
            throw new AccessException("Access denied");
        }
    }
}
