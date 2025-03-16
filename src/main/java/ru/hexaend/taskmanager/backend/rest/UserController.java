package ru.hexaend.taskmanager.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hexaend.taskmanager.backend.dto.request.ChangeProfileRequest;
import ru.hexaend.taskmanager.backend.dto.response.TaskResponse;
import ru.hexaend.taskmanager.backend.dto.response.UserProfile;
import ru.hexaend.taskmanager.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User operations")
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    @Operation(summary = "Change user profile",
            description = "Change user profile. Only authenticated users can change their profile.")
    public UserProfile getUser() {
        return userService.getProfile();
    }

    @PostMapping("/me")
    @Operation(summary = "Change user profile",
            description = "Change user profile. Only authenticated users can change their profile.")
    public UserProfile changeUser(@Valid @RequestBody ChangeProfileRequest changeProfileRequest) {

        String sub = userService.getSubFromJwt();
        userService.changeProfile(changeProfileRequest, sub);
        return userService.getProfile();
    }

    @GetMapping("/tasks")
    @Operation(summary = "Get user tasks",
            description = "Get user tasks. Only authenticated users can get their tasks.")
    public List<TaskResponse> getUserTasks(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        String sub = userService.getSubFromJwt();
        return userService.getTasks(page, size, sub);
    }

}
