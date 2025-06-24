package se.jimmyemanuelsson.receptfix.backend.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends ApiException {
    public UsernameAlreadyExistsException(String username) {
        super("user/username-exists", "Username already in use: " + username, HttpStatus.CONFLICT.value());
    }
}