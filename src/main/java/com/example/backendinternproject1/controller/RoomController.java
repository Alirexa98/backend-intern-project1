package com.example.backendinternproject1.controller;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.dto.RoomDTO;
import com.example.backendinternproject1.service.RoomService;
import com.example.backendinternproject1.service.RoomServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

  private RoomService roomService;

  @PostMapping("/{roomId}/join/{userId}")
  public ResponseEntity joinRoom(@PathVariable @NotBlank String roomId, @PathVariable @NotBlank String userId) {
    var r = roomService.joinRoom(roomId, userId);
    return ResponseEntity.ok(r);
  }

  @PostMapping("/{roomId}/leave/{userId}")
  public ResponseEntity leaveRoom(@PathVariable @NotBlank String roomId, @PathVariable @NotBlank String userId) {
    roomService.leaveRoom(roomId, userId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{roomId}/{userId}")
  public ResponseEntity removeRoom(@PathVariable @NotBlank String roomId, @PathVariable @NotBlank String userId) {
    roomService.removeRoom(roomId, userId);
    return ResponseEntity.ok().build();
  }

  @PostMapping()
  public ResponseEntity createRoom(@RequestBody RoomDTO roomDTO) {
    var r = roomService.createRoom(roomDTO);
    return ResponseEntity.status(201).body(RoomDTO.of(r));
  }

  @GetMapping()
  public ResponseEntity getAllRooms() {
    return ResponseEntity.ok(roomService.getRooms().stream().map(RoomDTO::of));
  }

  @GetMapping("/{roomId}/messages")
  public ResponseEntity getAllMessages(@PathVariable @NotBlank String roomId) {
    var r = roomService.getRoom(roomId);
    return ResponseEntity.ok(r.getMessages().stream().map(MessageDTO::of));
  }
}
