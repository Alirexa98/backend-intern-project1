package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.entity.MessageEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.NotAllowedActionException;

import javax.validation.Valid;
import java.time.LocalDateTime;

public interface MessageService {
  MessageEntity getMessage(Long messageId);

  void sendMessage(@Valid MessageDTO messageDTO);

  void editMessage(@Valid MessageDTO messageDTO);
}
