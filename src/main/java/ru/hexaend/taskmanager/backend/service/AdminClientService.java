package ru.hexaend.taskmanager.backend.service;

public interface AdminClientService {
    void addNewAdmin(String username) throws RuntimeException;

    void addNewManager(String username);
}
