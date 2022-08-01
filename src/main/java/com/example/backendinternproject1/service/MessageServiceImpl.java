package com.example.backendinternproject1.service;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.entity.MessageEntity;
import com.example.backendinternproject1.exception.EntityNotFoundException;
import com.example.backendinternproject1.exception.NotAllowedActionException;
import com.example.backendinternproject1.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@Validated
@Transactional
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  public static final int MESSAGE_EDIT_TIME_IN_MINUTES = 10;

  private MessageRepository messageRepository;
  private UserServiceImpl userService;
  private RoomServiceImpl roomService;

  public MessageEntity getMessage(Long messageId) {
    var message = messageRepository.findById(messageId);
    if (message.isEmpty()) {
      throw new EntityNotFoundException(String.valueOf(messageId));
    }
    return message.get();
  }

  public void sendMessage(@Valid MessageDTO messageDTO) {
    userService.checkUserLoggedIn(messageDTO.getUserId());
    var r = roomService.getRoom(messageDTO.getRoomId());
    var u = userService.getUser(messageDTO.getUserId());
    if (!r.haveUser(u.getUserId())) {
      throw new NotAllowedActionException();
    }
    userService.loginOrRefreshUser(u.getUserId());
    messageRepository.save(
        new MessageEntity(
            null,
            messageDTO.getText(),
            LocalDateTime.now(),
            u,
            r
        )
    );
  }

  public void editMessage(@Valid MessageDTO messageDTO) {
    userService.checkUserLoggedIn(messageDTO.getUserId());
    var r = roomService.getRoom(messageDTO.getRoomId());
    var u = userService.getUser(messageDTO.getUserId());
    var m = getMessage(messageDTO.getMessageId());
    if (!m.getSender().getUserId().equals(u.getUserId()) || !m.getRoom().getRoomId().equals(r.getRoomId())) {
      throw new NotAllowedActionException();
    }
    if (m.getSendTime().plusMinutes(MESSAGE_EDIT_TIME_IN_MINUTES).isBefore(LocalDateTime.now())) {
      throw new NotAllowedActionException();
    }
    userService.loginOrRefreshUser(u.getUserId());
    m.setText(messageDTO.getText());
    messageRepository.save(m);
  }

}
