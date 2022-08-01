package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.RoomDTO;
import com.example.backendinternproject1.entity.RoomEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.IdTakenException;
import com.example.backendinternproject1.exception.NotAllowedActionException;
import com.example.backendinternproject1.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Service
@Validated
@Transactional
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

  private static final int ROOMS_MAX_SIZE = 5;
  private RoomRepository roomRepository;
  private UserServiceImpl userService;

  public RoomEntity getRoom(String roomId) {
    var room = roomRepository.findById(roomId);
    if (room.isEmpty()) {
      throw new EntityNotFoundException(roomId);
    }
    return room.get();
  }

  public List<RoomEntity> getRooms() {
    return roomRepository.findAll();
  }

  public RoomEntity createRoom(@Valid RoomDTO roomDTO) {
    if (roomRepository.existsById(roomDTO.getRoomId())) {
      throw new IdTakenException(roomDTO.getRoomId());
    }
    var c = userService.getUser(roomDTO.getCreatorId());
    userService.checkUserLoggedIn(c.getUserId());
    userService.loginOrRefreshUser(c.getUserId());

    return roomRepository.save(
        new RoomEntity(
            roomDTO.getRoomId(),
            roomDTO.getName(),
            1,
            c,
            new HashSet<>() {{add(c);}},
            null
        )
    );
  }

  public void removeRoom(String roomId, String userId) {
    var r = getRoom(roomId);
    if (!r.getCreator().getUserId().equals(userId)) {
      throw new NotAllowedActionException();
    }
    var u = userService.getUser(userId);
    userService.checkUserLoggedIn(u.getUserId());
    userService.loginOrRefreshUser(u.getUserId());
    roomRepository.delete(r);
  }

  public RoomEntity joinRoom(String roomId, String userId) {
    var r = getRoom(roomId);
    var u = userService.getUser(userId);
    userService.checkUserLoggedIn(userId);
    userService.loginOrRefreshUser(userId);
    if (r.getUsers().size() < ROOMS_MAX_SIZE) {
      r.addUser(u);
      return roomRepository.save(r);
    } else {
      throw new NotAllowedActionException();
    }
  }

  public void leaveRoom(String roomId, String userId) {
    var r = getRoom(roomId);
    if (r.getCreator().getUserId().equals(userId)) {
        roomRepository.delete(r);
        return;
    }
    var u = userService.getUser(userId);
    userService.checkUserLoggedIn(userId);
    userService.loginOrRefreshUser(userId);
    r.removeUser(u);
    roomRepository.save(r);
  }

}
