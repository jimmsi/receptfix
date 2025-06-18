package se.jimmyemanuelsson.receptfix.backend.controller;

import se.jimmyemanuelsson.receptfix.backend.exceptions.InvalidLoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<?> handleInvalidLogin(InvalidLoginException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", ex.getMessage()));
    }

}