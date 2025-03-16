package ru.hexaend.taskmanager.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hexaend.taskmanager.backend.dto.request.ChangeProfileRequest;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;
import ru.hexaend.taskmanager.backend.dto.response.UserProfile;
import ru.hexaend.taskmanager.backend.model.Project;
import ru.hexaend.taskmanager.backend.model.Task;
import ru.hexaend.taskmanager.backend.model.User;
import ru.hexaend.taskmanager.backend.repository.ProjectRepository;
import ru.hexaend.taskmanager.backend.repository.UserRepository;
import ru.hexaend.taskmanager.backend.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserProfile getProfile() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        String sub = (String) claims.get("sub");

        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(sub);

        UserRepresentation userRepresentation = userResource.toRepresentation();


        return UserProfile
                .builder()
                .username(userRepresentation.getUsername())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .roles(userResource.roles().realmLevel().listEffective()
                        .stream()
                        .map(r -> r.getName())
                        .filter(s -> s.startsWith("ROLE_"))
                        .map(s -> s.substring(5))
                        .toList())
                .build();
    }

    @Override
    public void changeProfile(ChangeProfileRequest changeProfileRequest, String sub) {

        UserRepresentation userRepresentation = keycloak.realm(realm)
                .users()
                .get(sub).toRepresentation();

        if (changeProfileRequest.getFirstName() != null)
            userRepresentation.setFirstName(changeProfileRequest.getFirstName());
        if (changeProfileRequest.getLastName() != null)
            userRepresentation.setLastName(changeProfileRequest.getLastName());
        if (changeProfileRequest.getUsername() != null)
            userRepresentation.setUsername(changeProfileRequest.getUsername());

        keycloak.realm(realm)
                .users()
                .get(userRepresentation.getId())
                .update(userRepresentation);
    }

    @Override
    @Transactional
    public void getOrCreateUser(String keycloakId, String username, String email, String firstName, String lastName){
        Optional<User> existingUser = userRepository.findByKeycloakId(keycloakId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .keycloakId(keycloakId)
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .build();
            userRepository.save(newUser);
        }
    }

    public String getSubFromJwt() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        return (String) claims.get("sub");
    }

    @Override
    public List<TaskResponse> getTasks(int page, int size, String sub) {
        User user = userRepository.findByKeycloakId(sub).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Project> projects = projectRepository.findAllByUsersContainingOrManagerListContaining(user,user);
        List<Task> tasks = projects.stream()
                .flatMap(project -> project.getTasks().stream())
                .toList();

        int start = page * size;
        int end = Math.min(start + size, tasks.size());

        if (start >= tasks.size()) {
            return Collections.emptyList();
        }

        return tasks.subList(start, end).stream()
                .map(TaskResponse::fromTask)
                .toList();

    }
}
