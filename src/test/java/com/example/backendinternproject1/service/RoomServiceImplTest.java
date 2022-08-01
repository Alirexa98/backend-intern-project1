package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.RoomDTO;
import com.example.backendinternproject1.entity.RoomEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.NotAllowedActionException;
import com.example.backendinternproject1.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.Optional;

import static com.example.backendinternproject1.TestValues.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomServiceImplTest {

  @Mock
  UserServiceImpl userService;
  @Mock
  RoomRepository roomRepository;
  @InjectMocks
  RoomServiceImpl clazzUnderTest;

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getRoomTest() {
    Mockito.when(roomRepository.findById(room.getRoomId())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> clazzUnderTest.getRoom(room.getRoomId()));

    Mockito.when(roomRepository.findById(room.getRoomId())).thenReturn(Optional.of(roomEntity));
    assertEquals(roomEntity, clazzUnderTest.getRoom(room.getRoomId()));
  }

  @Test
  void getRoomsTest() {
    var expected = new LinkedList<RoomEntity>(){{add(roomEntity);}};
    Mockito.when(roomRepository.findAll()).thenReturn(expected);
    assertEquals(expected, clazzUnderTest.getRooms());
  }

  @Test
  void createRoomTest() {
    Mockito.when(roomRepository.existsById(room.getRoomId())).thenReturn(true);
    assertThrows(IdTakenException.class, () -> clazzUnderTest.createRoom(room));

    Mockito.when(roomRepository.existsById(room.getRoomId())).thenReturn(false);
    Mockito.when(userService.getUser(room.getCreatorId())).thenReturn(userEntity);
    Mockito.when(userService.loginOrRefreshUser(room.getCreatorId())).thenReturn(userEntity);
    Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
    var actual = clazzUnderTest.createRoom(room);
    assertEquals(roomEntity, actual);
  }

  @Test
  void removeRoomTest() {
    Mockito.when(roomRepository.findById(room.getRoomId())).thenReturn(Optional.of(roomEntity));
    assertThrows(NotAllowedActionException.class, () -> clazzUnderTest.removeRoom(room.getRoomId(), "another"));

    Mockito.when(userService.getUser(room.getCreatorId())).thenReturn(userEntity);

    clazzUnderTest.removeRoom(room.getRoomId(), room.getCreatorId());
  }

  @Test
  void joinRoomTest() {
    Mockito.when(roomRepository.findById(roomEntity.getRoomId())).thenReturn(Optional.of(roomEntity));
    Mockito.when(userService.getUser(room.getCreatorId())).thenReturn(userEntity);
    Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);

    assertEquals(roomEntity, clazzUnderTest.joinRoom(room.getRoomId(), room.getCreatorId()));
  }

  @Test
  void leaveRoomTest() {
    Mockito.when(roomRepository.findById(roomEntity.getRoomId())).thenReturn(Optional.of(roomEntity));
    Mockito.when(userService.getUser(room.getCreatorId())).thenReturn(userEntity);
    clazzUnderTest.leaveRoom(room.getRoomId(), room.getCreatorId());

    Mockito.when(userService.getUser(room.getCreatorId())).thenReturn(userEntity2);
    clazzUnderTest.leaveRoom(room.getRoomId(), "another");
  }


  @Test
  void entityToDTO() {
    assertNull(RoomDTO.of(null));

    var actual = RoomDTO.of(roomEntity);
    assertEquals(room, actual);
  }
}