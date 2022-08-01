package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.UserDTO;
import com.example.backendinternproject1.entity.UserEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.UserNotLoggedInException;
import com.example.backendinternproject1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import redis.clients.jedis.Jedis;


import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  public static final int USER_LOGOUT_TIME_SECONDS = 15*60;

  private UserRepository userRepository;
  private Jedis jedis;

  public UserEntity getUser(String userId) {
    var user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new EntityNotFoundException(userId);
    }
    return user.get();
  }

  public UserEntity registerUser(@Valid UserDTO user) {
    if (userRepository.existsById(user.getUserId()))
      throw new IdTakenException(user.getUserId());
    return userRepository.save(new UserEntity(
        user.getUserId(),
        user.getName(),
        user.getAvatar(),
        user.getBio(),
        null)
    );
  }

  public UserEntity loginOrRefreshUser(String userId) {
    var u = getUser(userId);
    jedis.set("login:" + userId, "t");
    jedis.expire("login:" + userId, USER_LOGOUT_TIME_SECONDS);
    return u;
  }

  public UserEntity logoutUser(String userId) {
    var u = getUser(userId);
    jedis.del("login:" + userId);
    return u;
  }

  public void checkUserLoggedIn(String userId) {
    if (!jedis.exists("login:" + userId)) {
      throw new UserNotLoggedInException(userId);
    }
  }
}
