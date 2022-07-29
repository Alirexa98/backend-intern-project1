package com.example.backendinternproject1.dto;


import com.example.backendinternproject1.entity.RoomEntity;
import com.example.backendinternproject1.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoomDTO {
  @JsonProperty("room_id")
  @NotBlank(message = "room Id should not be blank")
  private String roomId;
  @NotBlank(message = "room name should not be blank")
  private String name;
  @NotBlank(message = "creator Id should not be blank")
  @JsonProperty("creator_id")
  private String creatorId;
  @JsonProperty(value = "user_counts")
  private Integer usersCount;

  public static RoomDTO of(RoomEntity room) {
    if (room == null) {
      return null;
    }
    return new RoomDTO(
        room.getRoomId(),
        room.getName(),
        room.getCreator().getUserId(),
        room.getUsersCount()
    );
  }
}
