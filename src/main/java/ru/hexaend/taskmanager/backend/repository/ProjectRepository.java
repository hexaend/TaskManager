package ru.hexaend.taskmanager.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hexaend.taskmanager.backend.model.Project;
import ru.hexaend.taskmanager.backend.model.User;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByUsersContainingOrManagerListContaining(User user, User manager);

}

