package ru.hexaend.taskmanager.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User profile response")
public class UserProfile {

    @Schema(description = "Username", example = "ivan_ivanov")
    private String username;

    @Schema(description = "First name", example = "Ivan")
    private String firstName;

    @Schema(description = "Last name", example = "Ivanov")
    private String lastName;

    @Schema(description = "Email", example = "ivan_ivanov@example.com")
    private String email;

    @Schema(description = "Roles", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private List<String> roles;

}
