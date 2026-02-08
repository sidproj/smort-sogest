package org.smortsogest.exception;

import org.smortsogest.dto.ResponseError;
import org.smortsogest.exception.auth.DuplicateEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ResponseError> handleDuplicateEmailException(DuplicateEmailException ex){
        ResponseError responseError = new ResponseError();
        responseError.setError("Duplicate email found!");
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleInvalidRequest(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().
                getFieldErrors().
                get(0).
                getDefaultMessage();
        ResponseError responseError = new ResponseError();
        responseError.setError(message);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFound(UserNotFoundException ex){
        ResponseError error = new ResponseError();
        error.setError(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(error);
    }
}
