package com.example.backendinternproject1.exception;

public class UserNotLoggedInException extends RuntimeException {

  public UserNotLoggedInException(String userId) {
    super("User not Logged In: Id=" + userId);
  }

}
