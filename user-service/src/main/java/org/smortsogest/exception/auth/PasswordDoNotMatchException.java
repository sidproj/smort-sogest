package org.smortsogest.exception.auth;

public class PasswordDoNotMatchException extends RuntimeException {
    public PasswordDoNotMatchException(String message) {
        super(message);
    }
    public PasswordDoNotMatchException(){
        super("Password do not match exception");
    }
}
