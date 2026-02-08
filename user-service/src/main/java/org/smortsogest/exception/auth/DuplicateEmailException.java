package org.smortsogest.exception.auth;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
    public DuplicateEmailException(){super("Duplicate email");}
}
