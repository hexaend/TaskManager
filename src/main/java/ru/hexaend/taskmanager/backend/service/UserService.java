package ru.hexaend.taskmanager.backend.service;

import ru.hexaend.taskmanager.backend.dto.request.ChangeProfileRequest;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;
import ru.hexaend.taskmanager.backend.dto.response.UserProfile;

import java.util.List;

public interface UserService {
    UserProfile getProfile();

    void changeProfile(ChangeProfileRequest changeProfileRequest, String sub);

    void getOrCreateUser(String keycloakId, String username, String email, String firstName, String lastName);

    String getSubFromJwt();

    List<TaskResponse> getTasks(int page, int size, String sub);
}
