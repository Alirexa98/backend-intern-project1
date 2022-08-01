package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.UserDTO;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.UserNotLoggedInException;
import com.example.backendinternproject1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static com.example.backendinternproject1.TestValues.user;
import static com.example.backendinternproject1.TestValues.userEntity;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  Jedis jedis;

  @InjectMocks
  UserServiceImpl clazzUnderTest;

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getUserTest() {
    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> clazzUnderTest.getUser(user.getUserId()));

    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(userEntity));
    assertEquals(userEntity, clazzUnderTest.getUser(user.getUserId()));
  }

  @Test
  void registerUserTest() {
    Mockito.when(userRepository.existsById(user.getUserId())).thenReturn(true);

    assertThrows(IdTakenException.class, () -> clazzUnderTest.registerUser(user));

    Mockito.when(userRepository.existsById(user.getUserId())).thenReturn(false);
    Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
    clazzUnderTest.registerUser(user);
  }

  @Test
  void loginUserTest() {
    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> clazzUnderTest.loginOrRefreshUser(user.getUserId()));

    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(userEntity));
    Mockito.when(jedis.set("login:"+user.getUserId(), "t")).thenReturn(null);
    Mockito.when(jedis.expire("login:"+user.getUserId(), UserServiceImpl.USER_LOGOUT_TIME_SECONDS)).thenReturn(0L);
    var result = clazzUnderTest.loginOrRefreshUser(user.getUserId());
    assertEquals(userEntity, result);
  }

  @Test
  void logoutUserTest() {
    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> clazzUnderTest.logoutUser(user.getUserId()));

    Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(userEntity));
    Mockito.when(jedis.del("login:"+user.getUserId())).thenReturn(0L);
    var result = clazzUnderTest.logoutUser(user.getUserId());
    assertEquals(userEntity, result);
  }

  @Test
  void checkLoginTest() {
    Mockito.when(jedis.exists("login:"+user.getUserId())).thenReturn(false);
    assertThrows(UserNotLoggedInException.class, () -> clazzUnderTest.checkUserLoggedIn(user.getUserId()));

    Mockito.when(jedis.exists("login:"+user.getUserId())).thenReturn(true);
    clazzUnderTest.checkUserLoggedIn(user.getUserId());
  }

  @Test
  void entityToDTO() {
    assertNull(UserDTO.of(null));

    var actual = UserDTO.of(userEntity);
    assertEquals(user, actual);
  }
}