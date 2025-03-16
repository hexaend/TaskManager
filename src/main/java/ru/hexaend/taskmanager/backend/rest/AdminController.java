package ru.hexaend.taskmanager.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hexaend.taskmanager.backend.service.AdminClientService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Admins", description = "Admin operations")
public class AdminController {

    private final Keycloak keycloak;
    private final AdminClientService adminClientService;

    @Operation(summary = "Add new admin",
            description = "Add new admin to the system. Admins have all permissions in the system.")
    @PutMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestParam String username) {
        adminClientService.addNewAdmin(username);
        return ResponseEntity.ok("Successful added admin");
    }


    @PutMapping("/add-manager")
    @Operation(summary = "Add new manager",
            description = "Add new manager to the system. Managers have all permissions in the system except for admin operations.")
    public ResponseEntity<?> addManager(@RequestParam String username) {
        adminClientService.addNewManager(username);
        return ResponseEntity.ok("Successful added manager");
    }

}
