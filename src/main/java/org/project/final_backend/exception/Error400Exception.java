package org.project.final_backend.exception;

public class Error400Exception extends RuntimeException{
    public Error400Exception(String message) {
        super(message);
    }
}
