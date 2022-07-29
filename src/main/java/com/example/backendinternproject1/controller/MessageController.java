package com.example.backendinternproject1.controller;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.service.MessageService;
import com.example.backendinternproject1.service.MessageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

  private MessageService messageService;

  @PostMapping
  public ResponseEntity sendMessage(@RequestBody MessageDTO messageDTO) {
    messageService.sendMessage(messageDTO);
    return ResponseEntity.status(201).build();
  }

  @PutMapping
  public ResponseEntity editMessage(@RequestBody MessageDTO messageDTO) {
    messageService.editMessage(messageDTO);
    return ResponseEntity.ok().build();
  }
}
