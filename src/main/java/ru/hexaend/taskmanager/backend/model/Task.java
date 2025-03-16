package ru.hexaend.taskmanager.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.hexaend.taskmanager.backend.dto.request.TaskRequest;
import ru.hexaend.taskmanager.backend.model.enums.TaskPriority;
import ru.hexaend.taskmanager.backend.model.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"project"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private LocalDateTime deadline;

    public static Task fromRequest(TaskRequest taskRequest, Project project) {
        if (taskRequest == null) {
            return null;
        }

        return Task.builder()
                .priority(taskRequest.getPriority())
                .status(taskRequest.getStatus())
                .project(project)
                .title(taskRequest.getTitle())
                .deadline(taskRequest.getDeadline())
                .description(taskRequest.getDescription())
                .build();

    }

}