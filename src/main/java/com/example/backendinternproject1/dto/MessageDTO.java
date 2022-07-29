package com.example.backendinternproject1.dto;

import antlr.debug.MessageEvent;
import com.example.backendinternproject1.entity.MessageEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MessageDTO {
  @JsonProperty(value = "message_id")
  private Long messageId;
  @Length(min = 1, max = 500, message = "text length should be between 1 and 500 characters")
  private String text;
  @NotBlank(message = "user Id should not be blank")
  @JsonProperty("user_id")
  private String userId;
  @NotBlank(message = "room Id should not be blank")
  @JsonProperty("room_id")
  private String roomId;

  public static MessageDTO of(MessageEntity message) {
    if (message == null) {
      return null;
    }
    return new MessageDTO(
        message.getMessageId(),
        message.getText(),
        message.getSender().getUserId(),
        message.getRoom().getRoomId()
    );
  }

}
