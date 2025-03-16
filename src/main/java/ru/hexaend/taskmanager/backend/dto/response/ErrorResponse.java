package ru.hexaend.taskmanager.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Error response")
public class ErrorResponse {

    @Schema(description = "Error code", example = "404")
    private int code;

    @Schema(description = "Error message", example = "User not found")
    private String message;

}
