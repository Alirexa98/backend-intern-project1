package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.RoomDTO;
import com.example.backendinternproject1.entity.RoomEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.NotAllowedActionException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

public interface RoomService {
  RoomEntity getRoom(String roomId);

  List<RoomEntity> getRooms();

  RoomEntity createRoom(@Valid RoomDTO roomDTO);

  void removeRoom(String roomId, String userId);

  RoomEntity joinRoom(String roomId, String userId);

  void leaveRoom(String roomId, String userId);
}
