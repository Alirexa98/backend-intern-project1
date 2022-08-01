package com.example.backendinternproject1;

import com.example.backendinternproject1.dto.MessageDTO;
import com.example.backendinternproject1.dto.RoomDTO;
import com.example.backendinternproject1.dto.UserDTO;
import com.example.backendinternproject1.entity.MessageEntity;
import com.example.backendinternproject1.entity.RoomEntity;
import com.example.backendinternproject1.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.HashSet;

public class TestValues {

  public static UserDTO user = new UserDTO("testId", "alireza", null, null);
  public static UserEntity userEntity = new UserEntity("testId", "alireza", null, null, null);

  public static UserEntity userEntity2 = new UserEntity("another", "alireza", null, null, null);

  public static RoomDTO room = new RoomDTO("testId", "alireza", "testId", 1);
  public static RoomEntity roomEntity = new RoomEntity("testId", "alireza", 1, userEntity, new HashSet<>(){{add(userEntity);}}, null);

  public static MessageDTO message = new MessageDTO(10L, "alireza", "testId", "testId");
  public static MessageEntity messageEntity = new MessageEntity(10L, "alireza", LocalDateTime.now(), userEntity, roomEntity);

}
