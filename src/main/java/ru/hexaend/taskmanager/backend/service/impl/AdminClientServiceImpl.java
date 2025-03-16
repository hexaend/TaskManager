package ru.hexaend.taskmanager.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hexaend.taskmanager.backend.service.AdminClientService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminClientServiceImpl implements AdminClientService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${groups.manager.path}")
    private String managerGroupPath;

    @Value("${groups.admin.path}")
    private String adminGroupPath;

    @Override
    public void addNewAdmin(String username) {
        addUserToGroup(username, adminGroupPath);
    }

    @Override
    public void addNewManager(String username) {
        addUserToGroup(username, managerGroupPath);
    }

    private void addUserToGroup(String username, String groupPath) {
        UserRepresentation userRepresentation = Optional.of(keycloak.realm(realm)
                .users()
                .search(username).getFirst()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GroupRepresentation groupRepresentation = keycloak.realm(realm).getGroupByPath(groupPath);

        keycloak.realm(realm)
                .users()
                .get(userRepresentation.getId())
                .joinGroup(groupRepresentation.getId());
    }

}
