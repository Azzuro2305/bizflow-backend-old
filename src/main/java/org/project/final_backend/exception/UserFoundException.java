package org.project.final_backend.exception;

public class UserFoundException extends RuntimeException{
    public UserFoundException(String message) {
        super(message);
    }
}
