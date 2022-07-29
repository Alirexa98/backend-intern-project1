package com.example.backendinternproject1.exception;

public class IdTakenException extends RuntimeException {

  public IdTakenException(String id) {
    super("This id is already taken: Id=" + id);
  }

}
