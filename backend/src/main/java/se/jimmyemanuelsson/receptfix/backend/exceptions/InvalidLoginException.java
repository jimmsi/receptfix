package se.jimmyemanuelsson.receptfix.backend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends ApiException {
    public InvalidLoginException() {
        super("auth/invalid-credentials", "Invalid email or password", HttpStatus.UNAUTHORIZED.value());
    }
}