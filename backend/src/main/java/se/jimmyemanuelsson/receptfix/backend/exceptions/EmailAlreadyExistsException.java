package se.jimmyemanuelsson.receptfix.backend.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException(String email) {
        super("user/email-exists", "Email already in use: " + email, HttpStatus.CONFLICT.value());
    }
}
