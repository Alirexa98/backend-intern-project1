package com.example.backendinternproject1.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String id) {
    super("Entity not found: Id=" + id);
  }

}
