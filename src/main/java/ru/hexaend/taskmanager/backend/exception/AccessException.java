package ru.hexaend.taskmanager.backend.exception;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}
