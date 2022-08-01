package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.NotAllowedActionException;
import com.example.backendinternproject1.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.backendinternproject1.TestValues.*;
import static org.junit.jupiter.api.Assertions.*;

class MessageServiceImplTest {

  @Mock
  MessageRepository messageRepository;
  @Mock
  UserServiceImpl userService;
  @Mock
  RoomServiceImpl roomService;

  @InjectMocks
  MessageServiceImpl clazzUnderTest;

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getMessage() {
    Mockito.when(messageRepository.findById(message.getMessageId())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> clazzUnderTest.getMessage(message.getMessageId()));

    Mockito.when(messageRepository.findById(message.getMessageId())).thenReturn(Optional.of(messageEntity));
    assertEquals(messageEntity, clazzUnderTest.getMessage(message.getMessageId()));
  }

  @Test
  void sendMessage() {
    Mockito.when(roomService.getRoom(message.getRoomId())).thenReturn(roomEntity);
    Mockito.when(userService.getUser(message.getUserId())).thenReturn(userEntity2);

    assertThrows(NotAllowedActionException.class, () -> clazzUnderTest.sendMessage(message));

    Mockito.when(userService.getUser(message.getUserId())).thenReturn(userEntity);
    clazzUnderTest.sendMessage(message);
  }

  @Test
  void editMessage() {
    Mockito.when(messageRepository.findById(message.getMessageId())).thenReturn(Optional.of(messageEntity));
    Mockito.when(roomService.getRoom(message.getRoomId())).thenReturn(roomEntity);
    Mockito.when(userService.getUser(message.getUserId())).thenReturn(userEntity2);
    assertThrows(NotAllowedActionException.class, () -> clazzUnderTest.editMessage(message));

    Mockito.when(userService.getUser(message.getUserId())).thenReturn(userEntity);
    messageEntity.setSendTime(LocalDateTime.now().minusMinutes(MessageServiceImpl.MESSAGE_EDIT_TIME_IN_MINUTES + 10));
    assertThrows(NotAllowedActionException.class, () -> clazzUnderTest.editMessage(message));

    messageEntity.setSendTime(LocalDateTime.now());
    clazzUnderTest.editMessage(message);
  }

  @Test
  void entityToDTO() {
    assertNull(MessageDTO.of(null));

    var actual = MessageDTO.of(messageEntity);
    assertEquals(message, actual);
  }
}