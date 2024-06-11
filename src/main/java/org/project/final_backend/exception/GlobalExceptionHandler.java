package org.project.final_backend.exception;

import org.project.final_backend.domain.utility.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponse<Void>> handleUserFoundException(UserFoundException ex) {
        HttpResponse<Void> response = new HttpResponse<>(null, ex.getMessage(), HttpStatus.BAD_REQUEST, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HttpResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        HttpResponse<Void> response = new HttpResponse<>(null, ex.getMessage(), HttpStatus.NOT_FOUND, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<HttpResponse<Void>> handleInvalidPasswordException(InvalidPasswordException ex) {
        HttpResponse<Void> response = new HttpResponse<>(null, ex.getMessage(), HttpStatus.UNAUTHORIZED, false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
