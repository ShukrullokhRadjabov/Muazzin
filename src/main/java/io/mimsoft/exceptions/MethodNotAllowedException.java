package io.mimsoft.exceptions;

public class MethodNotAllowedException extends RuntimeException{
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
