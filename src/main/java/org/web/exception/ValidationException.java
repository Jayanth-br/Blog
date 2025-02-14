package org.web.exception;

public class ValidationException extends RuntimeException{

    private final String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }
}
