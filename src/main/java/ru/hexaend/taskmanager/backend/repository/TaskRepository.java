package ru.hexaend.taskmanager.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hexaend.taskmanager.backend.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
