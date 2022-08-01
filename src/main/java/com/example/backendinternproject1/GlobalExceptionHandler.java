package com.example.backendinternproject1;

import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.NotAllowedActionException;
import com.example.backendinternproject1.exception.UserNotLoggedInException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity entityNotFound(EntityNotFoundException exception) {
    return ResponseEntity.status(404).body(new ExceptionDTO(exception.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity idTaken(IdTakenException exception) {
    return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity notAllowedAction(NotAllowedActionException exception) {
    return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity userNotLoggedIn(UserNotLoggedInException exception) {
    return ResponseEntity.status(404).body(new ExceptionDTO(exception.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity constraintException(ConstraintViolationException exception) {
    return ResponseEntity.status(404).body(new ExceptionDTO(exception.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity catchAll(Exception exception) {
    return ResponseEntity.status(500).body(new ExceptionDTO("Unknown Exception!"));
  }

  @AllArgsConstructor
  @Getter
  private class ExceptionDTO {
    private String message;
  }
}
