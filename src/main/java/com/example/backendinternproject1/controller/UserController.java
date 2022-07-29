package com.example.backendinternproject1.controller;

import com.example.backendinternproject1.dto.UserDTO;
import com.example.backendinternproject1.service.UserService;
import com.example.backendinternproject1.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @PostMapping
  public ResponseEntity register(@RequestBody UserDTO userDTO) {
    var u = userService.registerUser(userDTO);
    return ResponseEntity.status(201).body(UserDTO.of(u));
  }

  @PostMapping("{userId}/login")
  public ResponseEntity login(@PathVariable("userId") @NotBlank String userId) {
    var u = userService.loginOrRefreshUser(userId);
    return ResponseEntity.ok().body(UserDTO.of(u));
  }

  @PostMapping("{userId}/logout")
  public ResponseEntity logout(@PathVariable("userId") @NotBlank String userId) {
    var u = userService.logoutUser(userId);
    return ResponseEntity.ok().body(UserDTO.of(u));
  }

  @GetMapping("/{userId}")
  public ResponseEntity profile(@PathVariable("userId") @NotBlank String userId) {
    var u = userService.getUser(userId);
    return ResponseEntity.ok().body(UserDTO.of(u));
  }

}
