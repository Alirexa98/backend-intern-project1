package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.UserDTO;
import com.example.backendinternproject1.entity.UserEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.UserNotLoggedInException;

import javax.validation.Valid;

public interface UserService {
  UserEntity getUser(String userId);

  UserEntity registerUser(@Valid UserDTO user);

  UserEntity loginOrRefreshUser(String userId);

  UserEntity logoutUser(String userId);

  void checkUserLoggedIn(String userId);
}
