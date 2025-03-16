package ru.hexaend.taskmanager.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hexaend.taskmanager.backend.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByKeycloakId(String keycloakId);

    Optional<User> findByKeycloakId(String keycloakId);

    Optional<User> findByUsername(String username);
}
