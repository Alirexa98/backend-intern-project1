package com.example.backendinternproject1.exception;

public class NotAllowedActionException extends RuntimeException {

  public NotAllowedActionException() {
    super("Not allowed action!");
  }

}
