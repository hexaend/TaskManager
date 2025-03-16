package ru.hexaend.taskmanager.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to change user profile")
public class ChangeProfileRequest {

    @Schema(description = "Username of the user", example = "ivan_ivanov")
    public String username;

    @Schema(description = "First name of the user", example = "Ivan")
    public String firstName;

    @Schema(description = "Last name of the user", example = "Ivanov")
    public String lastName;


    @AssertTrue(message = "At least one field must be filled")
    private boolean isAtLeastOneFieldFilled() {
        return username != null && !username.trim().isEmpty() ||
                firstName != null && !firstName.trim().isEmpty() ||
                lastName != null && !lastName.trim().isEmpty();
    }

}
