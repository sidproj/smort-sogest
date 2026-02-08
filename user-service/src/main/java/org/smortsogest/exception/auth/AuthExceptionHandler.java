package org.smortsogest.exception.auth;

import org.smortsogest.controller.AuthController;
import org.smortsogest.dto.ResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<ResponseError> handlePasswordDoNotMatch(PasswordDoNotMatchException ex){
        ResponseError error = new ResponseError();
        error.setError(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ResponseError> handelDuplicateEmail(DuplicateEmailException ex){
        ResponseError error = new ResponseError();
        error.setError(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(error);
    }

}
