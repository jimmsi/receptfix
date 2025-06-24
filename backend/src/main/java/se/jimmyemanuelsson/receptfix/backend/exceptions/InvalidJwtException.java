package se.jimmyemanuelsson.receptfix.backend.exceptions;

public class InvalidJwtException extends ApiException {
    public InvalidJwtException() {
        super("auth/invalid-jwt", "Invalid or expired token", 401);
    }

    public InvalidJwtException(String description, Throwable cause) {
        super("auth/invalid-jwt", description, 401, cause);
    }
}
